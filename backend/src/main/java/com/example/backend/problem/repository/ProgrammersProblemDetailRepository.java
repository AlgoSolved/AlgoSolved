package com.example.backend.problem.repository;

import com.example.backend.problem.domain.ProgrammersProblemDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammersProblemDetailRepository
        extends JpaRepository<ProgrammersProblemDetail, Long> {}
