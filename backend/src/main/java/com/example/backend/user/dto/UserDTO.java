package com.example.backend.user.dto;

import com.example.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    private String username;
    private Long solutionCount;
    private String githubLink;

    public static UserDTO mapToDTO(User user, Long solutionCount) {
        return UserDTO.builder()
                .username(user.getUsername())
                .solutionCount(solutionCount)
                .githubLink(user.getGithubUrl())
                .build();
    }
}
