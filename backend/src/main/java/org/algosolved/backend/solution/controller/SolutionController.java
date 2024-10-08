package org.algosolved.backend.solution.controller;

import lombok.RequiredArgsConstructor;

import org.algosolved.backend.common.response.BaseResponse;
import org.algosolved.backend.solution.dto.SolutionDTO;
import org.algosolved.backend.solution.dto.SolutionDetailDTO;
import org.algosolved.backend.solution.response.SolutionStatus;
import org.algosolved.backend.solution.service.SolutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/solutions")
public class SolutionController {

    private final SolutionService solutionService;

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

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SolutionDetailDTO>> getSolutionDetail(
            @PathVariable("id") Long id) {
        SolutionDetailDTO solutionDetailDto = solutionService.getSolution(id);

        return new ResponseEntity(
                BaseResponse.success(
                        SolutionStatus.SUCCESS.getCode(),
                        SolutionStatus.SUCCESS.getMessage(),
                        solutionDetailDto),
                HttpStatus.OK);
    }
}
