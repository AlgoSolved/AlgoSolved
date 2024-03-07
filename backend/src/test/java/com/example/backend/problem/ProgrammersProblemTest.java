package com.example.backend.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.problem.domain.ProgrammersProblem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ProgrammersProblemTest {
    @Nested
    @DisplayName("ProgrammersProblem 인스턴스 메서드 테스트")
    class InstanceMethodsTest {
        @Test
        @DisplayName("프로그래머스 문제 getLink 메서드 테스트")
        void getProgrammersProblemLinkTest() {
            ProgrammersProblem programmersProblem =
                    ProgrammersProblem.builder()
                            .title("title")
                            .lessonNumber(1000L)
                            .level(1)
                            .build();
            String expected = "https://programmers.co.kr/learn/courses/30/lessons/1000";
            String result = programmersProblem.getLink();
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("프로그래머스 문제 getRank 메서드 테스트")
        void getProgrammersProblemRankTest() {
            ProgrammersProblem programmersProblem =
                    ProgrammersProblem.builder()
                            .title("title")
                            .lessonNumber(1000L)
                            .level(1)
                            .build();
            String result = programmersProblem.getRank();
            String expected = "1";
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("프로그래머스 문제 getNumber 메서드 테스트")
        void getProgrammersProblemNumberTest() {
            ProgrammersProblem programmersProblem =
                    ProgrammersProblem.builder()
                            .title("title")
                            .lessonNumber(1000L)
                            .level(1)
                            .build();
            Long result = programmersProblem.getNumber();
            Long expected = 1000L;
            assertThat(result).isEqualTo(expected);
        }
    }
}
