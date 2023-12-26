package com.example.backend.solution.service;

import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolutionService {
	private final SolutionRepository solutionRepository;
	private final int perPage = 10;

	public Page<Solution> getAllSolutions(int page, String order) {
		Pageable pageRequest = PageRequest.of(page, perPage, Sort.by(Sort.Direction.fromString(order), "id"));
		Page<Solution> allSolutions =  solutionRepository.findAll(pageRequest);
		return allSolutions;
	}

	public Solution getSolutionById(Long id) {
		return solutionRepository.findById(id).orElseThrow();
	}
}
