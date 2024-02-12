package com.example.backend.solution.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.lib.GithubClient;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.response.SolutionStatus;
import com.example.backend.solution.service.SolutionService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    private final GithubClient githubClient;

    @Value("${github.app.privateKey}")
    private String test;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        boolean result = githubClient.isPathExist("youngjun0627/Rgorithm");
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/recent-list")
    public ResponseEntity<List<SolutionDTO>> getRecentSolutions() {
        List<SolutionDTO> solutionAllList = solutionService.getRecentSolutions();

        if (solutionAllList.isEmpty()) {
            return new ResponseEntity(
                    BaseResponse.success(
                            SolutionStatus.SUCCESS_EMPTY_VALUE.getCode(),
                            SolutionStatus.SUCCESS_EMPTY_VALUE.getMessage(),
                            Collections.emptyList()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(
                            SolutionStatus.SUCCESS.getCode(),
                            SolutionStatus.SUCCESS.getMessage(),
                            solutionAllList),
                    HttpStatus.OK);
        }
    }
}
