package com.example.backend.problem.repository;

import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProgrammersProblemDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgrammersProblemDetailRepository
        extends JpaRepository<ProgrammersProblemDetail, Long> {

    Optional<ProgrammersProblemDetail> findByProblem(Problem problem);
}
