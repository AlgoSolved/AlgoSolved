package com.example.backend.repository;

import com.example.backend.domain.solution.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

}
