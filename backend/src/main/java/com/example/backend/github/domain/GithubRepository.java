package com.example.backend.github.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.solution.domain.Solution;
import com.example.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "github_repositories")
public class GithubRepository extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String repo;

    @OneToMany(mappedBy = "githubRepository")
    private List<Solution> solutions = new ArrayList<>();

    @Builder
    public GithubRepository(User user, String repo) {
        this.user = user;
        this.repo = repo;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
