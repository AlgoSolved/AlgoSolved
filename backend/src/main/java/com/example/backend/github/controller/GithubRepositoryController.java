package com.example.backend.github.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.common.response.ResponseStatus;
import com.example.backend.github.service.SyncWithGithubService;

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
                    BaseResponse.success(ResponseStatus.SUCCESS, true), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(ResponseStatus.SUCCESS, false), HttpStatus.OK);
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<Boolean> sync(@RequestBody Map<String, Object> payload) {
        // TODO: 현재 유저와 동일한지 확인필요
        // github repository 스키마 변경해야될 듯
        // token 필요없을 듯

        Integer githubRepositoryId = (Integer) payload.get("githubRepositoryId");

        if (syncWithGithubService.fetch(githubRepositoryId.longValue())) {
            return new ResponseEntity(
                    BaseResponse.success(ResponseStatus.SUCCESS, true), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(ResponseStatus.SUCCESS, false), HttpStatus.OK);
        }
    }
}
