package com.example.backend.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    /** 2xx : 성공 */
    SUCCESS("2000", "요청에 성공하였습니다.", HttpStatus.OK),
    SUCCESS_EMPTY_VALUE("2001", "최근 문제 풀이가 없습니다.", HttpStatus.OK),

    /** 4xx : Client 오류 */
    NOT_FOUND("4040", "데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("4041", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
