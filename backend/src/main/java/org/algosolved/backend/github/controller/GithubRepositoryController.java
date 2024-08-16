package org.algosolved.backend.github.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.algosolved.backend.common.response.BaseResponse;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.github.repository.GithubRepositoryRepository;
import org.algosolved.backend.github.response.GithubRepositoryStatus;
import org.algosolved.backend.github.service.SyncWithGithubService;
import org.algosolved.backend.problem.service.ProblemService;
import org.algosolved.backend.solution.service.SolutionService;
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
