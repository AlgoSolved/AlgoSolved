package org.algosolved.backend.problem.repository;

import org.algosolved.backend.problem.domain.ProgrammersProblem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammersProblemRepository extends JpaRepository<ProgrammersProblem, Long> {
    ProgrammersProblem findByLessonNumber(Long lessonNumber);
}
