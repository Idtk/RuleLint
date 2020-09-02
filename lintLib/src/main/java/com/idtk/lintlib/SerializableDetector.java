package com.idtk.lintlib;
import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UAnonymousClass;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Time:2020/8/31
 * Author:Idtk
 * Desc: 序列化对象，内部变量检查
 */
public class SerializableDetector extends Detector implements Detector.UastScanner {

    private static final String CLASS_SERIALIZABLE = "java.io.Serializable";
    private static final ArrayList<String> baseType = new ArrayList<>(Arrays.asList(
//            String.class.getCanonicalName(),
            byte.class.getCanonicalName(),
            short.class.getCanonicalName(),
            int.class.getCanonicalName(),
            long.class.getCanonicalName(),
            float.class.getCanonicalName(),
            double.class.getCanonicalName(),
            boolean.class.getCanonicalName(),
            char.class.getCanonicalName(),
            Byte.class.getCanonicalName(),
            Short.class.getCanonicalName(),
            Integer.class.getCanonicalName(),
            Long.class.getCanonicalName(),
            Float.class.getCanonicalName(),
            Double.class.getCanonicalName(),
            Boolean.class.getCanonicalName(),
            Character.class.getCanonicalName()));

    public static final Issue INNER_FIELD_SERIALIZABLE = Issue.create(
            "InnerFieldSerializable",
            "内部非基本类型的变量类需要实现Serializable接口",
            "内部非基本类型的变量类需要实现Serializable接口",
            Category.CORRECTNESS, 5, Severity.ERROR,
            new Implementation(SerializableDetector.class, Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<String> applicableSuperClasses() {
        return Collections.singletonList(CLASS_SERIALIZABLE);
    }
    /**
     * 扫描到applicableSuperClasses()指定的list时,回调该方法
     */
    @Override
    public void visitClass(JavaContext context, UClass declaration) {
        //只从最外部开始向内部类递归检查
        if (declaration instanceof UAnonymousClass) {
            return;
        }
        innerField(context, declaration);
    }



    private void innerField(JavaContext context, UClass declaration) {
        for (UField uField:declaration.getFields()){
            String className = uField.getType().getCanonicalText();
//            System.out.println(" Serializable:field:"+className);
            if (uField.getType().getCanonicalText().contains("java.lang") || baseType.contains(uField.getType().getCanonicalText())){
                continue;
            }
            JavaEvaluator javaEvaluator = context.getEvaluator();
            String clazzFullName = getTargetStr(className);
//            System.out.println(" Serializable:clazzFullName:"+clazzFullName);
            if (javaEvaluator.extendsClass(declaration,CLASS_SERIALIZABLE, true)) {
                if (!javaEvaluator.extendsClass(javaEvaluator.findClass(clazzFullName), CLASS_SERIALIZABLE, false)) {
//                    context.report(ISSUE,context.getLocation(javaEvaluator.findClass(clazzFullName)),clazzFullName+" 需要实现 "+CLASS_SERIALIZABLE);
                    context.report(INNER_FIELD_SERIALIZABLE,
                            uField.getNameIdentifier(),
                            context.getLocation(uField.getNameIdentifier()),
                            String.format("变量 `%1$s` 的类`%2$s`,需要实现Serializable接口", uField.getName(),uField.getType().getCanonicalText()));
                }
            }
        }
    }

    // 提取<>之间的泛型类型的完整字符串
    private String getTargetStr(String s){
        int start = 0;
        int end = s.length();
        if (s.contains("<")){
            start = s.indexOf("<")+1;
        }
        if (s.contains(">")){
            end = s.indexOf(">");
        }
        return s.substring(start,end);
    }
}