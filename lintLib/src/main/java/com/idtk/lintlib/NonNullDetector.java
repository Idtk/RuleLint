package com.idtk.lintlib;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UAnonymousClass;
import org.jetbrains.uast.UClass;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time:2020/9/1
 * Author:Idtk
 * Desc: !! 使用警告
 */
public class NonNullDetector extends Detector implements Detector.UastScanner {

    private static final String CLASS_OBJECT = "java.lang.Object";

    public static final Issue NON_NULL = Issue.create(
            "Non Null",
            "请注意!!的使用",
            "请注意!!的使用",
            Category.SECURITY, 6, Severity.WARNING,
            new Implementation(NonNullDetector.class, Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<String> applicableSuperClasses() {
        return Collections.singletonList(CLASS_OBJECT);
    }

    @Override
    public void visitClass(JavaContext context, UClass declaration) {
        if (declaration instanceof UAnonymousClass) {
            return;
        }
        if (!LintUtils.isKotlin(declaration)){
            return;
        }
        String source = context.getPsiFile().getText();
        int index = source.indexOf("!!");
        if (index < 0) return;
//        System.out.println(" non_null:index:"+index);
//        System.out.println(" non_null:source:"+source);
        /*context.report(NON_NULL,
                declaration.getNameIdentifier(),
                context.getLocation(declaration.getNameIdentifier()),
                "请注意!!的使用");*/

        Pattern pattern = Pattern.compile("!!");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()){
            context.report(NON_NULL,
                    Location.create(context.file,source,matcher.start(),matcher.end()),
                    "请注意!!的使用");
        }
    }

}
