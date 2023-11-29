package com.example.backend.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    // TODO: many to one 관계로 providers 테이블과 연결

    // TODO: one to many 관계로 solutions 테이블과 연결

    public Problem(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
