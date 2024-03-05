package com.example.backend.problem.repository;

import com.example.backend.problem.domain.BaekjoonProblem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonProblemRepository extends JpaRepository<BaekjoonProblem, Long> {
    BaekjoonProblem findByProblemNumber(Long problemNumber);
}
