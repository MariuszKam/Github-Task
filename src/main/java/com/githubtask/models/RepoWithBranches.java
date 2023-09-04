package com.githubtask.models;

import java.util.List;

public record RepoWithBranches (GitRepo repo, List<Branch> branches) {}
