package org.algosolved.backend.problem.repository;

import org.algosolved.backend.problem.domain.BaekjoonProblem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaekjoonProblemRepository extends JpaRepository<BaekjoonProblem, Long> {
    BaekjoonProblem findByProblemNumber(Long problemNumber);
}
