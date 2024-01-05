package com.example.backend.problem.repository;

import com.example.backend.problem.domain.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {}
