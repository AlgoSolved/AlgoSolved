package com.example.backend.batch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubSyncDto {

    private String file;
    private String sourceCode;
}
