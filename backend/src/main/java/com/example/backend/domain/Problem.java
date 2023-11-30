package com.example.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public Problem(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
