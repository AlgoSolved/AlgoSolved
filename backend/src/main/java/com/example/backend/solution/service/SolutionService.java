package com.example.backend.solution.service;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.dto.SolutionDetailDTO;
import com.example.backend.solution.repository.SolutionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public List<SolutionDTO> getRecentSolutions() {
        List<Solution> solutions = solutionRepository.findTop10ByOrderByCreatedAtDesc();

        return solutions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public SolutionDetailDTO getSolution(Long id) {
        Solution solution =
                solutionRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        return SolutionDetailDTO.mapToDTO(solution);
    }

    private SolutionDTO mapToDTO(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemProvider(solution.getProblem().getProvider().getName());
        solutionDTO.setProblemNumber(solution.getProblem().getId().toString());
        solutionDTO.setProblemName(solution.getProblem().getTitle());
        solutionDTO.setUserName(solution.getGithubRepository().getUser().getUsername());
        return solutionDTO;
    }
}
