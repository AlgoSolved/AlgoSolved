package com.example.backend.repository;

import com.example.backend.domain.problem.ProgrammersProblemDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammersProblemDetailRepository extends JpaRepository<ProgrammersProblemDetail, Long> {
}
