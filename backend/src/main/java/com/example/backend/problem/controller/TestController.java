package com.example.backend.problem.controller;

import com.example.backend.problem.domain.Problem;
import com.example.backend.solution.domain.Solution;
import com.example.backend.problem.repository.ProblemRepository;

import com.example.backend.solution.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableJpaAuditing
public class TestController {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @GetMapping("/test")
    public void createProblem() {
        Problem problem = new Problem("title", "content");
        problemRepository.save(problem);

        Solution solution = new Solution();
        solution.setProblem(problem);

        solutionRepository.save(solution);
    }
}
