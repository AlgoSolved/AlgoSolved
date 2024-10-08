package org.algosolved.backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserJwtDto {

    private Long id;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserJwtDto(Long id, String name, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }
}
