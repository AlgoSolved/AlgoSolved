package com.example.backend.solution.controller;

import com.example.backend.common.response.BaseResponse;
import com.example.backend.common.response.ResponseStatus;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.service.SolutionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    @GetMapping("/recent-list")
    public ResponseEntity<List<SolutionDTO>> getRecentSolutions() {
        List<SolutionDTO> solutionAllList = solutionService.getRecentSolutions();

        if (solutionAllList.isEmpty()) {
            return new ResponseEntity(
                    BaseResponse.success(
                            ResponseStatus.SUCCESS_EMPTY_VALUE, Collections.emptyList()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    BaseResponse.success(ResponseStatus.SUCCESS, solutionAllList), HttpStatus.OK);
        }
    }
}
