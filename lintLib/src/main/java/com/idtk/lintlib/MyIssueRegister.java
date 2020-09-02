package com.idtk.lintlib;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyIssueRegister extends IssueRegistry {

    private static final List<Issue> sIssues;

    static {
        List<Issue> issues = new ArrayList<>();
        issues.add(LateinitWarnDetector.LATEINIT_WARN);
//        issues.add(LogDetector.ISSUE);
        issues.add(SerializableDetector.INNER_FIELD_SERIALIZABLE);
        issues.add(NonNullDetector.NON_NULL);
        sIssues = Collections.unmodifiableList(issues);
    }

    @NotNull
    @Override
    public List<Issue> getIssues() {
//        System.out.println(" ==== start issue ====");
        return sIssues;
    }
}
