package com.example.backend.solution.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.problem.domain.Problem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "solutions")
@NoArgsConstructor
@Getter
@Setter
public class Solution extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "github_repository_id")
  private GithubRepository githubRepository;

  private String language;

  @Column(name = "source_code")
  private String sourceCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id")
  private Problem problem;

  @Builder
  public Solution(
    GithubRepository githubRepository,
    String language,
    String sourceCode,
    Problem problem
  ) {
      this.githubRepository = githubRepository;
      this.language = language;
      this.sourceCode = sourceCode;
      this.problem = problem;
  }
}
