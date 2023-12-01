package com.example.backend.repository;

import com.example.backend.domain.problem.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
