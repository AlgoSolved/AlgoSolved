package com.example.backend.domain;


import com.example.backend.common.converters.RoleTypeListConverter;
import com.example.backend.common.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
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

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id")
    private Repository repository;

    private String username;

    private String name;

    @Convert(converter = RoleTypeListConverter.class)
    private List<RoleType> roles;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}
