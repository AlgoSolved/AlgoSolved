package com.example.backend.solution.controller;

import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.handler.ResponseHandler;
import com.example.backend.solution.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
public class SolutionController {
	// TODO: 테스트 구현 필요
	private final SolutionService solutionService;

	@GetMapping("/solutions")
	public ResponseEntity<Object> getAllSolutions(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "order", defaultValue = "desc") String order
	) {
		Page<Solution> allSolutions = solutionService.getAllSolutions(page, order);
		return new ResponseHandler().solutionsWithUser(allSolutions);
	}

	@GetMapping("/solutions/{id}")
	public ResponseEntity<Object> getSolutionById(
		@PathParam(value = "id") @PathVariable Long id
	) {
		Solution solution = solutionService.getSolutionById(id);
		return new ResponseHandler().solution(solution);
	}
}
