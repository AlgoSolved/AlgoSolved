package org.algosolved.backend.user.dto;

import lombok.Builder;
import lombok.Getter;

import org.algosolved.backend.user.domain.User;

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
