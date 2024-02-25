package com.example.backend.problem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

@Entity
@Table(name = "baekjoon_problem_details")
@Getter
@Setter
public class BaekjoonProblemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "link")
    private String link;

    @Column(name = "tier")
    private String tier;

    @Builder
    public BaekjoonProblemDetail(Problem problem, String link, String tier) {
        this.problem = problem;
        this.link = link;
        this.tier = tier;
    }
}
