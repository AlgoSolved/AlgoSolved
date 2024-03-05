package com.example.backend.github.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.response.GithubRepositoryStatus;
import com.example.backend.github.service.SyncWithGithubService;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.service.SolutionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/github-repositories")
public class GithubRepositoryController {

    private final SyncWithGithubService syncWithGithubService;
    private final ProblemService problemService;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final SolutionService solutionService;

    @PostMapping("/connect")
    public ResponseEntity<Boolean> connect(@RequestBody Map<String, Object> payload) {
        String owner = (String) payload.get("owner");
        String repo = (String) payload.get("repo");

        if (syncWithGithubService.connect(owner, repo)) {
            return new ResponseEntity(
                    BaseResponse.success(
                            GithubRepositoryStatus.SUCCESS.getCode(),
                            GithubRepositoryStatus.SUCCESS.getMessage(),
                            true),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(
                            GithubRepositoryStatus.SUCCESS.getCode(),
                            GithubRepositoryStatus.SUCCESS.getMessage(),
                            false),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/sync")
    public ResponseEntity<Integer> sync(@RequestBody Map<String, Object> payload) {
        // TODO: 현재 유저와 동일한지 확인필요

        int count = 0;

        Long githubRepositoryId = Long.parseLong(String.valueOf(payload.get("githubRepositoryId")));
        GithubRepository githubRepository =
                githubRepositoryRepository.findById(githubRepositoryId).get();

        List<String[]> solutionFiles = syncWithGithubService.fetch(githubRepository);

        for (String[] fileAndCode : solutionFiles) {
            String file = fileAndCode[0];
            String sourceCode = fileAndCode[1];
            Problem problem = problemService.getOrCreateFromFile(file);

            LanguageType languageType = LanguageType.getLanguageType(file);
            Solution solution =
                    solutionService.createSolution(
                            githubRepository, problem, languageType, sourceCode);
            if (solution != null) {
                count++;
            }
        }

        return new ResponseEntity(
                BaseResponse.success(
                        GithubRepositoryStatus.SUCCESS.getCode(),
                        GithubRepositoryStatus.SUCCESS.getMessage(),
                        count),
                HttpStatus.OK);
    }
}
