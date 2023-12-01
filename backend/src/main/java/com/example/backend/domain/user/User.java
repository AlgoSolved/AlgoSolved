package com.example.backend.domain.user;


import com.example.backend.domain.BaseTimeEntity;
import com.example.backend.domain.githubRepository.GithubRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql="UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user")
    private GithubRepository repository;

    private String username;

    private String name;

    @Convert(converter = RoleListConverter.class)
    private List<Role> roles;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public User(
        String username,
        String name,
        String profileImageUrl,
        String githubUrl
    ) {
        // TODO: null 인 경우 default 값 설정
        this.username = username;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.githubUrl = githubUrl;
    }
}
