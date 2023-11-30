package com.example.backend.controller;

import com.example.backend.domain.Problem;
import com.example.backend.repository.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@EnableJpaAuditing
public class TestController {
    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping("/test")
    public void createProblem() {
        Problem problem = new Problem("title", "content");
        problemRepository.save(problem);
    }
}