package com.example.backend.solution.dto;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.problem.domain.BaekjoonProblemDetail;
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

    public static SolutionDetailDTO mapToDTO(Solution solution) {
        Problem problem = solution.getProblem();
        String link;

        if (problem == null || problem.getProvider() == null) {
            throw new NotFoundException(ExceptionStatus.NOT_FOUND);
        }

        link = switch (problem.getProvider().getName()) {
            default -> problem.getBaekjoonProblemDetail().getLink();
        };

        if (problem.getProvider().getName().equals(BaekjoonProblemDetail.class.toString())) {
            link = problem.getBaekjoonProblemDetail().getLink();
        }

        return SolutionDetailDTO.builder()
                .language(solution.getLanguage())
                .sourceCode(solution.getSourceCode())
                .problemName(problem.getTitle())
                .problemNumber(problem.getNumber())
                .link(link)
                .build();
    }
}
