package com.example.backend.model;

import javax.persistence.*;

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


}
