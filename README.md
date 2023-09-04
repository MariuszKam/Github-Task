# GitHub User Repository Info API

This is a Java Spring Boot RESTful API that provides repository and branch details for a given GitHub user. The API retrieves non-fork repositories and their branches, including the last commit SHA for each branch.

## Features

-   Retrieves non-fork repositories details for the given GitHub user.
-   Lists the branches for each repository along with the last commit SHA for each branch.

## Classes and Packages

1.  **Model Classes**  (`com.githubtask.models`)

    -   `Branch`: Represents a GitHub branch.
    -   `Commit`: Represents a GitHub commit.
    -   `GitRepo`: Represents a GitHub repository.
    -   `Owner`: Represents the GitHub user who owns a repository.
    -   `RepoWithBranches`: Represents a repository along with its branches.
2.  **Service Classes**  (`com.githubtask.service`)

    -   `GitHubService`: Retrieves user repository details and branches from the GitHub API.
3.  **Response Classes**  (`com.githubtask.responses`)

    -   `BranchDetails`: Response model for branch data.
    -   `RepoResponse`: Response model for repository data.
    -   `ErrorResponse`: Response model for error messages.
4.  **Exceptions**  (`com.githubtask.exceptions`)

    -   `UserNotFoundException`: Thrown if a GitHub user cannot be found.
    -   `UnsupportedAcceptHeaderException`: Thrown if an unsupported Accept header is provided.
5.  **Controllers**  (`com.githubtask.controllers`)

    -   `Controller`: Handles incoming requests and maps them to the corresponding service layer.
    -   `ApplicationExceptionHandler`: Handles exceptions and returns appropriate error responses.

## Prerequisites

-   JDK 17
-   Maven



## Usage

Endpoint:

`GET /api/{username}`

Replace  `{username}`  with the desired GitHub username.

Example request:

`GET /api/johndoe`

Example response:

`[
{
"name": "example-repo",
"owner": "johndoe",
"branches": [
{
"name": "main",
"sha": "e72d939d50f0aa359ab94e824a23d0d218e82d21"
}
]
}
]`

## Error Handling

The API will return error messages and status codes in case of these **two** exceptions:

1.  If the specified user is not found, it returns 404 status code:

`{
"status": 404,
"message": "User with username johndoe not found."
}`

2.  If the provided Accept header is "application/xml", it returns 406 status code:

`{
"status": 406,
"message": "Header 'application/xml' is unsupported for the Accept header."
}`