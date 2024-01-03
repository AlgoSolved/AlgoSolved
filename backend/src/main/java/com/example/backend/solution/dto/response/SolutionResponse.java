package com.example.backend.solution.dto.response;

import com.example.backend.solution.domain.Solution;
import lombok.Builder;

@Builder
public record SolutionResponse(
	Long id,
	String problemProvider,
	String problemTitle,
	String repositoryUrl
) {

	public static SolutionResponse from(Solution solution) {
		return SolutionResponse.builder()
			.id(solution.getId())
			.problemProvider(solution.getProblem().getProvider().getName())
			.problemTitle(solution.getProblem().getTitle())
			.repositoryUrl(solution.getGithubRepository().getUrl())
			.build();
	}
}
