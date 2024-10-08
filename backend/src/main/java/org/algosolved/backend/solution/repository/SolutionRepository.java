package org.algosolved.backend.solution.repository;

import org.algosolved.backend.solution.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findTop10ByOrderByCreatedAtDesc();

    Boolean existsByHashKey(String hashKey);
}
