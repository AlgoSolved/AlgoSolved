package com.example.backend.solution.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.problem.domain.Problem;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.util.Sha256Generator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(name = "source_code")
    private String sourceCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "hash_key")
    private String hashKey;

    @Builder
    public Solution(
            GithubRepository githubRepository,
            LanguageType language,
            String sourceCode,
            Problem problem) {
        this.githubRepository = githubRepository;
        this.language = language;
        this.sourceCode = sourceCode;
        this.problem = problem;
        setHashKey();
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void setHashKey() {
        this.hashKey = this.generateHashKey();
    }

    public String generateHashKey() {
        Sha256Generator sha256Generator = new Sha256Generator();
        String message =
                this.githubRepository.getRepo()
                        + this.problem.getTitle()
                        + this.language
                        + this.sourceCode;
        return sha256Generator.encrypt(message);
    }
}
