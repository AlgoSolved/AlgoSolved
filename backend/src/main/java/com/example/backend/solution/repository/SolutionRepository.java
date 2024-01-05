package com.example.backend.solution.repository;

import com.example.backend.solution.domain.Solution;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findTop10ByOrderByCreatedAtDesc();
}
