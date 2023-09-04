package com.githubtask.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BranchDetails {
    private String name;
    private String sha;
}
