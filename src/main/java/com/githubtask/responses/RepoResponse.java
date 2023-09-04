package com.githubtask.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RepoResponse {
    private String name;
    private String owner;
    private List<BranchDetails> branches;
}
