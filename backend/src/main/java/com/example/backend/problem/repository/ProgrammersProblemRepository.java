package com.example.backend.problem.repository;

import com.example.backend.problem.domain.ProgrammersProblem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammersProblemRepository extends JpaRepository<ProgrammersProblem, Long> {
    ProgrammersProblem findByLessonNumber(Long lessonNumber);
}
