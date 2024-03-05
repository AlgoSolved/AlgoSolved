package com.example.backend.problem.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProblemFactory {
    public Problem getFromFile(String file) {
        String[] strings = file.split("/");
        String problemType = strings[0];
        String tier = strings[1];
        Long problemNumber = Long.parseLong(strings[2].split("\\.")[0]);
        String problemTitle = strings[2].split("\\.")[1];
        if (problemType.equals("백준")) {
            return BaekjoonProblem.builder().title(problemTitle).tier(tier).problemNumber(problemNumber).build();
        } else if (problemType.equals("프로그래머스")) {
            return ProgrammersProblem.builder().title(problemTitle).level(Integer.parseInt(tier)).lessonNumber(problemNumber).build();
        } else {
            throw new IllegalArgumentException("Invalid problem type");
        }
    }
}
