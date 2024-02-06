package com.example.backend.solution.dto;

import com.example.backend.solution.domain.Solution;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolutionDetailDTO {

    private String language;
    private String sourceCode;
    private String problemName;

    public static SolutionDetailDTO mapToDTO(Solution solution) {
        SolutionDetailDTO solutionDetailDto = new SolutionDetailDTO();
        solutionDetailDto.setLanguage(solution.getLanguage());
        solutionDetailDto.setProblemName(solution.getProblem().getTitle());
        return solutionDetailDto;
    }
}
