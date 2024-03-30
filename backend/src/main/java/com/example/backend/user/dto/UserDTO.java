package com.example.backend.user.dto;

import com.example.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;

public class UserDTO {

    @Getter
    @Builder
    public static class Profile {

        private String username;
        private Long solutionCount;
        private String githubLink;

        public static Profile mapToDTO(User user, Long solutionCount) {
            return Profile.builder()
                    .username(user.getUsername())
                    .solutionCount(solutionCount)
                    .githubLink(user.getGithubUrl())
                    .build();
        }
    }
}
