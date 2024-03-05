package com.example.backend.problem.service;

import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProblemFactory;
import com.example.backend.problem.domain.ProgrammersProblem;
import com.example.backend.problem.repository.BaekjoonProblemRepository;
import com.example.backend.problem.repository.ProgrammersProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final BaekjoonProblemRepository baekjoonProblemRepository;
    private final ProgrammersProblemRepository programmersProblemRepository;
    private final ProblemFactory problemFactory;


    public Problem getOrCreateFromFile(String file) {
        Problem problem = problemFactory.getFromFile(file);
        if (problem instanceof BaekjoonProblem) {
            return findOrCreateBaekjoonProblem(problem);
        } else if (problem instanceof ProgrammersProblem) {
            return findOrCreateProgrammersProblem(problem);
        } else {
            throw new IllegalArgumentException("Invalid problem type");
        }
    }

    private Problem findOrCreateBaekjoonProblem(Problem _problem) {
        Problem problem = baekjoonProblemRepository.findByProblemNumber(_problem.getNumber());
        if (problem == null) {
            baekjoonProblemRepository.save((BaekjoonProblem)_problem);
            problem = _problem;
        }
        return problem;
    }

    private Problem findOrCreateProgrammersProblem(Problem _problem) {
        Problem problem = programmersProblemRepository.findByLessonNumber(_problem.getNumber());
        if (problem == null) {
            programmersProblemRepository.save((ProgrammersProblem)_problem);
            problem = _problem;
        }
        return problem;
    }
}
