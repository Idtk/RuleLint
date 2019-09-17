package com.idtk.lintlib;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class MyIssueRegister extends IssueRegistry {

    @NotNull
    @Override
    public List<Issue> getIssues() {
//        System.out.println(" ==== start issue ====");
        return Arrays.asList(LogDetector.ISSUE,SerializableDetector.ISSUE);
    }
}
