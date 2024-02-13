package com.example.backend.problem.domain;

import com.example.backend.common.BaseTimeEntity;
import com.example.backend.solution.domain.Solution;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "problems")
@Getter
@RequiredArgsConstructor
public class Problem extends BaseTimeEntity {

    @OneToMany(mappedBy = "problem")
    private final List<Solution> solutions = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private Long number;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content", length = 10_000, nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @OneToOne(mappedBy = "problem", fetch = FetchType.LAZY)
    private BaekjoonProblemDetail baekjoonProblemDetail;

    @Builder
    public Problem(String title, String content, Provider provider) {
        this.title = title;
        this.content = content;
        this.provider = provider;
    }
}
