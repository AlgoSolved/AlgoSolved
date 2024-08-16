package org.algosolved.backend.problem.repository;

import org.algosolved.backend.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {}
