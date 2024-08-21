package org.algosolved.backend.solution.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.algosolved.backend.problem.domain.Problem;
import org.algosolved.backend.solution.domain.Solution;

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

    public static SolutionDetailDTO mapToDTO(Solution solution) {
        Problem problem = solution.getProblem();

        return SolutionDetailDTO.builder()
                .language(solution.getLanguage().getName())
                .sourceCode(solution.getSourceCode())
                .problemName(problem.getTitle())
                .problemNumber(problem.getNumber())
                .link(problem.getLink())
                .rank(problem.getRank())
                .build();
    }
}
