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

    private static final String REPO_API_URL = "https://api.github.com/users/%s/repos";
    private static final String BRANCH_API_URL = "https://api.github.com/repos/%s/%s/branches";
    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public List<RepoWithBranches> getRepo (String username) {
        List<GitRepo> repos;
        try {
            ResponseEntity<List<GitRepo>> response = restTemplate.exchange(
                    String.format(REPO_API_URL, username),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            repos = response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("User with username " + username + " not found.");
        }
        repos = repos.stream()
                .filter(repo -> !repo.fork())
                .collect(Collectors.toList());

        List<RepoWithBranches> reposWithBranches = new ArrayList<>();
        try {
            for (GitRepo repo:repos
                 ) {
                ResponseEntity<List<Branch>> branchesResponse = restTemplate.exchange(
                        String.format(BRANCH_API_URL, username, repo.name()),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
                List<Branch> branches = branchesResponse.getBody();
                reposWithBranches.add(new RepoWithBranches(repo, branches));
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("User with username " + username + " not found.");
        }
        return reposWithBranches;
    }
}
