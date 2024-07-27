package org.algosolved.backend.github.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GithubRepositoryStatus {
    SUCCESS("2000", "요청에 성공하였습니다."),
    FAILURE("4000", "요청에 실패하였습니다.");

    private final String code;
    private final String message;
}
