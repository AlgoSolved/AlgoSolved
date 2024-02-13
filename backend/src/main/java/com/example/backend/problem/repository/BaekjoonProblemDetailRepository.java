package com.example.backend.problem.repository;

import com.example.backend.problem.domain.BaekjoonProblemDetail;
import com.example.backend.problem.domain.Problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaekjoonProblemDetailRepository
        extends JpaRepository<BaekjoonProblemDetail, Long> {

    Optional<BaekjoonProblemDetail> findByProblem(Problem problem);
}
