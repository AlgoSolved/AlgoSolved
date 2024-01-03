package com.example.backend.solution.dto.response;

import com.example.backend.dto.PageInfo;
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
}
