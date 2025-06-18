package org.algosolved.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserTokenResponseDto {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserSignInResponseDto {
        private String accessToken;
        private String refreshToken;
    }
}
