package com.example.backend.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    SUCCESS("2000", "요청에 성공하였습니다.");

    private final String code;
    private final String message;
}
