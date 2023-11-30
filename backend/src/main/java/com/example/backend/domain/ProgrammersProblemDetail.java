package com.example.backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "programmers_problem_details")
@Getter
public class ProgrammersProblemDetail  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Problem.class)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "level")
    private Integer level;
}
