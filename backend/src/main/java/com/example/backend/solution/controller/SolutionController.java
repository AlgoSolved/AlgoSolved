package com.example.backend.solution.controller;

import com.example.backend.dto.PageInfo;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.dto.response.SolutionResponse;
import com.example.backend.solution.dto.response.SolutionsResponse;
import com.example.backend.solution.service.SolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SolutionController {
	// TODO: 테스트 구현 필요
	private final SolutionService solutionService;

	@GetMapping("/solutions")
	public ResponseEntity<SolutionsResponse> getAllSolutions(
		@PageableDefault(size = Solution.PER_PAGE) Pageable pageable,
		@RequestParam(value = "order", defaultValue = "desc") String order
	) {
		int size = 10;
		Page<Solution> allSolutions = solutionService.getAllSolutions(pageable, order);
		PageInfo pageInfo = new PageInfo(
			allSolutions.getPageable().getPageNumber(),
			size,
			allSolutions.getTotalPages(),
			allSolutions.getTotalElements()
		);

		List<SolutionResponse> solutionResponses = allSolutions.stream().map(SolutionResponse::from).toList();

		return new ResponseEntity<SolutionsResponse> (
			new SolutionsResponse(pageInfo, solutionResponses),
			HttpStatus.OK
		);
	}

	@GetMapping("/solutions/{id}")
	public ResponseEntity<SolutionResponse> getSolutionById(
		@PathVariable("id") Long id
	) {
		Solution solution = solutionService.getSolutionById(id);
		return new ResponseEntity<SolutionResponse> (
			SolutionResponse.from(solution),
			HttpStatus.OK
		);
	}
}
