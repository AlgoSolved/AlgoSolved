package com.example.backend.solution.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolutionStatus {
    SUCCESS("2000", "요청에 성공하였습니다."),
    SUCCESS_EMPTY_VALUE("2001", "최근 문제 풀이가 없습니다.");

    private final String code;
    private final String message;
}
