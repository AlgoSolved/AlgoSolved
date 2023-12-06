package com.example.backend.solution.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.problem.domain.Problem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "solutions")
@Getter @Setter
public class Solution extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "github_repository_id")
  private GithubRepository githubRepository;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;
}
