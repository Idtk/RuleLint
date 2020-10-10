package com.idtk.lintlib;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * Time:2020/9/24
 * Author:Idtk
 * Desc: 检查代码中未判空情况下使用!!的情况，但是暂时无法支持扩展函数的检查
 *       因为是检查的ByteCode，所以不会在源码中显示警告，可在报告中可以查看
 * <pre>
 *     // 会警告
 *      tv.text = a!!.name
 *     // 不会警告
 *     if (a != null) {
 *          tv.text = a!!.name
 *     }
 * </pre>
 */

public class NullSafeDetector extends Detector implements Detector.ClassScanner{

    public static final Issue NULL_SAFE = Issue.create(
            "NullSafeDetector",
            "请注意!!的使用",
            "请注意!!的使用",
            Category.SECURITY,
            5, /*1-10*/
            Severity.WARNING,
            new Implementation(NullSafeDetector.class, Scope.CLASS_FILE_SCOPE));


    @Nullable
    @Override
    public List<String> getApplicableCallNames() {
        return Arrays.asList("throwNpe");
    }

    @Override
    public void checkCall(@NotNull ClassContext context, @NotNull ClassNode classNode, @NotNull MethodNode method, @NotNull MethodInsnNode call) {
        super.checkCall(context, classNode, method, call);
        /*System.out.println("NullSafeDetector: "+call.owner+":"+call.getOpcode());
        List<LocalVariableNode> localVariables = method.localVariables;
        for (LocalVariableNode var:localVariables) {
            System.out.println("NullSafeDetector list: "+var.name+":"+var.desc);
        }*/
        if ("kotlin/jvm/internal/Intrinsics".equals(call.owner)){
            context.report(NULL_SAFE, method,call,context.getLocation(call),"请注意!!的使用");
        }
    }
}
