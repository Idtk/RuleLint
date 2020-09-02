package com.idtk.lintlib;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

/**
 * log工具检查
 */
public class LogDetector extends Detector implements Detector.UastScanner{

    public static final Issue ISSUE = Issue.create(
            "LogUsage",
            "请使用项目中提供的Log工具",
            "避免在项目中直接使用android.log以及system.out",
            Category.SECURITY,
            5, /*1-10*/
            Severity.WARNING,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("v", "d", "i", "w", "e", "println", "print");
    }

    @Override
    public void visitMethod(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "android.util.Log") || context.getEvaluator().isMemberInClass(method, "java.io.PrintStream")) {
            /*PsiMethod[] classes = context.getCallLocation(node,true,true);
            for (PsiMethod uClass:classes){
                System.out.println("test1111"+uClass);
            }*/
            System.out.println("test1111: "+context.getCallLocation(node,true,true));
            context.report(ISSUE, node, context.getLocation(node), "应该使用项目中的Log工具!");
        }
    }
}
