package com.example.backend.github.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.response.GithubRepositoryStatus;
import com.example.backend.github.service.SyncWithGithubService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/github-repositories")
public class GithubRepositoryController {

    private final SyncWithGithubService syncWithGithubService;
    private final GithubRepositoryRepository githubRepositoryRepository;

    @PostMapping("/connect")
    public ResponseEntity<Boolean> connect(@RequestBody Map<String, Object> payload) {
        String owner = (String) payload.get("owner");
        String repo = (String) payload.get("repo");
        Boolean result = syncWithGithubService.connect(owner, repo);

        return new ResponseEntity(
                BaseResponse.success(
                        GithubRepositoryStatus.SUCCESS.getCode(),
                        GithubRepositoryStatus.SUCCESS.getMessage(),
                        result),
                HttpStatus.OK);
    }

    @PostMapping("/sync")
    public ResponseEntity<Integer> sync(@RequestBody Map<String, Object> payload) {
        // TODO: 현재 유저와 동일한지 확인필요

        Long githubRepositoryId = Long.parseLong(String.valueOf(payload.get("githubRepositoryId")));
        GithubRepository githubRepository =
                githubRepositoryRepository.findById(githubRepositoryId).get();

        syncWithGithubService.fetch(githubRepository);

        return new ResponseEntity(
                BaseResponse.success(
                        GithubRepositoryStatus.SUCCESS.getCode(),
                        GithubRepositoryStatus.SUCCESS.getMessage(),
                        ""),
                HttpStatus.OK);
    }
}
