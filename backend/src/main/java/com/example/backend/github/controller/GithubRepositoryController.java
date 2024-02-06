package com.example.backend.github.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.github.response.GithubRepositoryStatus;
import com.example.backend.github.service.SyncWithGithubService;

import com.example.backend.solution.response.SolutionStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/github-repositories")
public class GithubRepositoryController {
    private final SyncWithGithubService syncWithGithubService;

    @PostMapping("/connect")
    public ResponseEntity<Boolean> connect(@RequestBody Map<String, Object> payload) {
        String owner = (String) payload.get("owner");
        String repo = (String) payload.get("repo");

        if (syncWithGithubService.connect(owner, repo)) {
            return new ResponseEntity(
                BaseResponse.success(GithubRepositoryStatus.SUCCESS.getCode(), GithubRepositoryStatus.SUCCESS.getMessage(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                BaseResponse.success(GithubRepositoryStatus.FAILURE.getCode(), GithubRepositoryStatus.FAILURE.getMessage(), false), HttpStatus.OK);
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<Boolean> sync(@RequestBody Map<String, Object> payload) {
        // TODO: 현재 유저와 동일한지 확인필요

        Integer githubRepositoryId = (Integer) payload.get("githubRepositoryId");

        if (syncWithGithubService.fetch(githubRepositoryId.longValue())) {
            return new ResponseEntity(
                    BaseResponse.success(GithubRepositoryStatus.SUCCESS.getCode(), GithubRepositoryStatus.SUCCESS.getMessage(), true), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(GithubRepositoryStatus.SUCCESS.getCode(), GithubRepositoryStatus.SUCCESS.getMessage(), false), HttpStatus.OK);
        }
    }
}
