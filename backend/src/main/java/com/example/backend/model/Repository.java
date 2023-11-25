package com.example.backend.model;

import javax.persistence.*;
import java.util.StringTokenizer;

@Entity
@Table(name = "repositories")
public class Repository extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String url;

    private String token;
}
