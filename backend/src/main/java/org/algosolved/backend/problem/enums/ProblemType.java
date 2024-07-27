package org.algosolved.backend.problem.enums;

public enum ProblemType {
    BaekjoonProblem("백준"),
    ProgrammersProblem("프로그래머스");
    ;

    private final String name;

    ProblemType(String name) {
        this.name = name;
    }
}
