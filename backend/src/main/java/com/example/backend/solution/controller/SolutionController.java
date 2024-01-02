package com.example.backend.solution.controller;

import com.example.backend.common.response.Response;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.service.SolutionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
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
  public ResponseEntity<Response<List<SolutionDTO>>> getRecentSolutions() {
    List<SolutionDTO> solutionDTOList = solutionService.getRecentSolutions();

    Response<List<SolutionDTO>> successResponse = Response.success(solutionDTOList);
    return ResponseEntity.ok(successResponse);

    //실패 응답
  }
}
