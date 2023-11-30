package com.example.backend.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "baekjoon_problem_details")
@Getter @Setter
public class BaekjoonProblemDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @Column(name = "tier")
  private String tier;

}
