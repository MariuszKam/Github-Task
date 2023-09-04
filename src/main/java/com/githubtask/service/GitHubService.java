package com.githubtask.service;


import com.githubtask.exceptions.UserNotFoundException;
import com.githubtask.models.Branch;
import com.githubtask.models.GitRepo;
import com.githubtask.models.RepoWithBranches;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<RepoWithBranches> getRepo (String username, String acceptHeader) {
        final String userURL = "https://api.github.com/users/" + username + "/repos";
        List<GitRepo> repos;
        try {
            ResponseEntity<List<GitRepo>> response = restTemplate.exchange(
                    userURL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<GitRepo>>() {}
            );
            repos = response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("User with username " + username + " not found.");
        }
        repos = repos.stream()
                .filter(repo -> !repo.isFork())
                .collect(Collectors.toList());

        List<RepoWithBranches> reposWithBranches = new ArrayList<>();
        final String reposURL = "https://api.github.com/repos/" + username;
        for (GitRepo repo:repos
             ) {
            ResponseEntity<List<Branch>> branchesResponse = restTemplate.exchange(
                    reposURL + "/" + repo.getName() + "/branches",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Branch>>() {}
            );
            List<Branch> branches = branchesResponse.getBody();
            reposWithBranches.add(new RepoWithBranches(repo, branches));
        }
        return reposWithBranches;
    }
}
