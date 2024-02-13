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
import lombok.Getter;

@Entity
@Table(name = "programmers_problem_details")
@Getter
public class ProgrammersProblemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Problem.class)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "link")
    private String link;

    @Column(name = "level")
    private Integer level;
}
