package com.githubtask.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RepoWithBranches {
    private final GitRepo repo;
    private final List<Branch> branches;
}
