package com.example.backend.problem.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.solution.domain.Solution;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "problems")
@Getter
@RequiredArgsConstructor
public class Problem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 10_000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToMany(mappedBy = "problem")
    private List<Solution> solutions = new ArrayList<>();

    @OneToOne(mappedBy = "problem", fetch = FetchType.LAZY)
    private BaekjoonProblemDetail baekjoonProblemDetail;

    @Builder
    public Problem(String title, String content, Provider provider) {
        this.title = title;
        this.content = content;
        this.provider = provider;
    }
}
