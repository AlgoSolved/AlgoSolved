package org.algosolved.backend.problem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "baekjoon_problems")
@Getter
@Setter
@DiscriminatorValue("BaekjoonProblem")
public class BaekjoonProblem extends Problem {
    @Column(name = "problem_number")
    private Long problemNumber;

    @Column(name = "tier")
    private String tier;

    public BaekjoonProblem() {}

    @Builder
    public BaekjoonProblem(String title, Long problemNumber, String tier) {
        super(title);
        this.problemNumber = problemNumber;
        this.tier = tier;
    }

    public String getLink() {
        return "https://www.acmicpc.net/problem/" + this.getProblemNumber();
    }

    public String getRank() {
        return this.tier;
    }

    public Long getNumber() {
        return this.problemNumber;
    }
}
