package com.githubtask.responses;

import java.util.List;

public record RepoResponse (String name, String owner, List<BranchDetails> branches) {}
