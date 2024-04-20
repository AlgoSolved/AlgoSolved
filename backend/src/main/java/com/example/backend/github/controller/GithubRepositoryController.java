package com.example.backend.github.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.response.GithubRepositoryStatus;
import com.example.backend.github.service.SyncWithGithubService;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.service.SolutionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jobrunr.scheduling.JobScheduler;
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
@Slf4j
public class GithubRepositoryController {

    private final SyncWithGithubService syncWithGithubService;
    private final ProblemService problemService;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final SolutionService solutionService;
    private final JobScheduler jobScheduler;

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
        Long githubRepositoryId = Long.parseLong(String.valueOf(payload.get("githubRepositoryId")));
        jobScheduler.enqueue(() -> syncWithGithubService.fetch(githubRepositoryId));

        return new ResponseEntity(
                BaseResponse.success(
                        GithubRepositoryStatus.SUCCESS.getCode(),
                        GithubRepositoryStatus.SUCCESS.getMessage(),
                        ""),
                HttpStatus.OK);
    }
}
