package com.example.backend.controller;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exception.NotFoundException;
import com.example.backend.domain.Problem;
import com.example.backend.domain.Solution;
import com.example.backend.repository.ProblemRepository;

import com.example.backend.repository.SolutionRepository;
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
        throw new NotFoundException(ExceptionStatus.USER_NOT_FOUND);
    }
}
