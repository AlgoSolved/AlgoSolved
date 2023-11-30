package com.example.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "github_repositories")
public class GithubRepository extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String url;

    private String token;

    @OneToMany(mappedBy = "github_repository")
//    @JoinColumn(name = "github_repository_id")
    private List<Solution> solutions = new ArrayList<>();
}
