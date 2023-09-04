package com.githubtask.controllers;


import com.githubtask.exceptions.UnsupportedAcceptHeaderException;
import com.githubtask.responses.BranchDetails;
import com.githubtask.responses.RepoResponse;
import com.githubtask.models.RepoWithBranches;
import com.githubtask.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    private final GitHubService gitHubService;

    public Controller(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/api/{username}")
    public ResponseEntity<List<RepoResponse>> getRepos(@PathVariable String username,
                                                       @RequestHeader("Accept") String acceptHeader) {
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(acceptHeader)) {
            throw new UnsupportedAcceptHeaderException("The Accept header value " + acceptHeader + " is unsupported.");
        }
        List<RepoWithBranches> reposWithBranches = gitHubService.getRepo(username);
        List<RepoResponse> repoList = reposWithBranches.stream().map(repoWithBranches -> {
            String repoName = repoWithBranches.repo().name();
            String ownerLogin = repoWithBranches.repo().owner().login();
            List<BranchDetails> branches = repoWithBranches.branches().stream()
                    .map(branch -> new BranchDetails(branch.name(), branch.commit().sha()))
                    .collect(Collectors.toList());

            return new RepoResponse(repoName, ownerLogin, branches);
        }).collect(Collectors.toList());

        return new ResponseEntity<>(repoList, HttpStatus.OK);
    }
}
