package com.githubtask.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepo {
    private final String name;
    private final Owner owner;
    private final boolean fork;
}
