package com.example.backend.domain.solution;

import com.example.backend.domain.BaseTimeEntity;
import com.example.backend.domain.githubRepository.GithubRepository;
import com.example.backend.domain.problem.Problem;
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
  private GithubRepository github_repository;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;
}
