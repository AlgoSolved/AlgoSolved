package com.example.backend.problem.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "programmers_problem_details")
@Getter
@NoArgsConstructor
public class ProgrammersProblemDetail {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Problem.class)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "link")
    private String link;

    @Column(name = "level")
    private Integer level;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public ProgrammersProblemDetail(Problem problem, String link, Integer level) {
        this.problem = problem;
        this.link = link;
        this.level = level;
    }
}
