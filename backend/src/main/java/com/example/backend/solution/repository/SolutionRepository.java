package com.example.backend.solution.repository;

import com.example.backend.solution.domain.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolutionRepository extends PagingAndSortingRepository<Solution, Long> {
	Page<Solution> findAll(Pageable pageable);
}
