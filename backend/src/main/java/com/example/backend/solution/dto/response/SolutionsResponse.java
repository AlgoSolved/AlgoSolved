package com.example.backend.solution.dto.response;

import com.example.backend.dto.PageInfo;
import com.example.backend.solution.domain.Solution;
import lombok.Builder;

import java.util.List;

@Builder
public record SolutionsResponse (
	PageInfo pageInfo,
	List<SolutionResponse> solutions
) {

	public SolutionsResponse (PageInfo pageInfo, List<SolutionResponse> solutions) {
		this.pageInfo = pageInfo;
		this.solutions = solutions;
	}

	public static SolutionsResponse from(PageInfo pageInfo, List<Solution> solutions) {
		List<SolutionResponse> solutionResponses = solutions.stream().map(SolutionResponse::from).toList();
		return new SolutionsResponse(pageInfo, solutionResponses);
	}
}
