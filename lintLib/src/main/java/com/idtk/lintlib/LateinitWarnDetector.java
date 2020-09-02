package com.idtk.lintlib;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UField;

import java.util.Collections;
import java.util.List;

/**
 * Time:2020/8/31
 * Author:Idtk
 * Desc: lateinit 使用警告
 */
public class LateinitWarnDetector extends Detector implements Detector.UastScanner {

    public static final Issue LATEINIT_WARN = Issue.create(
            "LateinitWarn",
            "请减少使用lateinit",
            "请尽量使用Lazy或构造参数初始化替代lateinit",
            Category.SECURITY, 6, Severity.WARNING,
            new Implementation(LateinitWarnDetector.class, Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<Class<? extends org.jetbrains.uast.UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UField.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(@NotNull JavaContext context) {
        if(!context.getMainProject().isAndroidProject()){
            return null;
        }

        return new Handler(context);
    }

    public class Handler extends UElementHandler{

        private JavaContext context;

        public Handler(JavaContext context) {
            super();
            this.context = context;
        }

        @Override
        public void visitField(@NotNull UField node) {
            if (!LintUtils.isKotlin(node)){
                return;
            }
//            System.out.println(" visitField:field:"+node.getName()+":"+node.getUastInitializer());
            if (node.getUastInitializer() == null){
                context.report(LATEINIT_WARN, node.getNameIdentifier(),
                        context.getLocation(node.getNameIdentifier()),
                        "请减少使用lateinit,尽量使用Lazy或构造参数初始化替代!");
            }
        }
    }
}
