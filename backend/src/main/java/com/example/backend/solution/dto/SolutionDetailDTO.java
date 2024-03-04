package com.example.backend.solution.dto;

import com.example.backend.problem.domain.Problem;
import com.example.backend.solution.domain.Solution;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SolutionDetailDTO {

    private String language;
    private String sourceCode;
    private String problemName;
    private Long problemNumber;
    private String link;
    private String rank;

    public static SolutionDetailDTO mapToDTO(Solution solution, String link, String rank) {
        Problem problem = solution.getProblem();

        return SolutionDetailDTO.builder()
                .language(solution.getLanguage())
                .sourceCode(solution.getSourceCode())
                .problemName(problem.getTitle())
                .problemNumber(problem.getNumber())
                .link(link)
                .rank(rank)
                .build();
    }
}
