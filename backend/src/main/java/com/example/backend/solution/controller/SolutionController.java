package com.example.backend.solution.controller;

import com.example.backend.common.response.Response;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.service.SolutionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solutions")
public class SolutionController {

  private final SolutionService solutionService;

  @GetMapping("/recent-list")
  public ResponseEntity<List<SolutionDTO>> getRecentSolutions() {
    List<SolutionDTO> solutionDTOList = solutionService.getRecentSolutions();

    return new ResponseEntity(Response.success(solutionDTOList), HttpStatus.OK);
  }
}
