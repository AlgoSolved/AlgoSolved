package org.algosolved.backend.solution.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolutionDTO {
    private Long id;
    private String problemType;
    private String problemNumber;
    private String problemName;
    private String userName;
}
