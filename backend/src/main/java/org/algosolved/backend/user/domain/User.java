package org.algosolved.backend.user.domain;

import org.algosolved.backend.common.BaseTimeEntity;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.user.common.converters.RoleTypeListConverter;
import org.algosolved.backend.user.common.enums.Role;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private GithubRepository githubRepository;

    private String username;

    private String name;

    @Convert(converter = RoleTypeListConverter.class)
    private List<Role> roles;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public User(
            String username,
            String name,
            String profileImageUrl,
            String githubUrl,
            String accessToken) {
        this.username = username;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.githubUrl = githubUrl;
        this.accessToken = accessToken;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
