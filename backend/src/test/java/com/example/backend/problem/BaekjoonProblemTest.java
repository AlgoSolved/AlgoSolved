package com.example.backend.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.problem.domain.BaekjoonProblem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BaekjoonProblemTest {
    @Nested
    @DisplayName("BaekjoonProblem 인스턴스 메서드 테스트")
    class InstanceMethodsTest {
        @Test
        @DisplayName("백준 문제 getLink 메서드 테스트")
        void getBaekjoonProblemRankTest() {
            BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder().title("title").problemNumber(1000L).tier("Bronze").build();
            String result = baekjoonProblem.getLink();
            String expected = "https://www.acmicpc.net/problem/1000";
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("백준 문제 getRank 메서드 테스트")
        void getBaekjoonProblemLinkTest() {
            BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder().title("title").problemNumber(1000L).tier("Bronze").build();
            String result = baekjoonProblem.getRank();
            String expected = "Bronze";
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("백준 문제 getNumber 메서드 테스트")
        void getBaekjoonProblemNumberTest() {
            BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder().title("title").problemNumber(1000L).tier("Bronze").build();
            Long result = baekjoonProblem.getNumber();
            Long expected = 1000L;
            assertThat(result).isEqualTo(expected);
        }
    }
}
