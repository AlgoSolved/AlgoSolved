package org.algosolved.backend.github.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.algosolved.backend.common.BaseTimeEntity;
import org.algosolved.backend.solution.domain.Solution;
import org.algosolved.backend.user.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Repository
@Table(name = "github_repositories")
public class GithubRepository extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String repo;

    @OneToMany(mappedBy = "githubRepository", cascade = CascadeType.REMOVE)
    private List<Solution> solutions = new ArrayList<>();

    @Builder
    public GithubRepository(User user, String repo) {
        this.user = user;
        this.repo = repo;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
