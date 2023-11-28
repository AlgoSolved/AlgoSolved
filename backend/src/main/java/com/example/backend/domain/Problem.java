package com.example.backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "problems")
@Getter
public class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    // TODO: many to one 관계로 providers 테이블과 연결

    // TODO: one to many 관계로 solutions 테이블과 연결
}
