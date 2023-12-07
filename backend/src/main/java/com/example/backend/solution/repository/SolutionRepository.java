package com.example.backend.solution.repository;

import com.example.backend.solution.domain.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

}
