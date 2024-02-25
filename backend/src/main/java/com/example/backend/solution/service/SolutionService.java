package com.example.backend.solution.service;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.problem.domain.BaekjoonProblemDetail;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProgrammersProblemDetail;
import com.example.backend.problem.repository.BaekjoonProblemDetailRepository;
import com.example.backend.problem.repository.ProgrammersProblemDetailRepository;
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
    private final BaekjoonProblemDetailRepository baekjoonProblemDetailRepository;
    private final ProgrammersProblemDetailRepository programmersProblemDetailRepository;

    public List<SolutionDTO> getRecentSolutions() {
        List<Solution> solutions = solutionRepository.findTop10ByOrderByCreatedAtDesc();

        return solutions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public SolutionDetailDTO getSolution(Long id) {
        Solution solution =
                solutionRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        Problem problem = solution.getProblem();

        if (problem == null || problem.getProvider() == null) {
            throw new NotFoundException(ExceptionStatus.NOT_FOUND);
        }

        if (problem.getProvider().getName().equals(BaekjoonProblemDetail.class.getSimpleName())) {
            return baekjoonProblemDetailToDTO(solution);
        } else {
            return programmersProblemDetailToDTO(solution);
        }
    }

    private SolutionDTO mapToDTO(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemProvider(solution.getProblem().getProvider().getName());
        solutionDTO.setProblemNumber(solution.getProblem().getId().toString());
        solutionDTO.setProblemName(solution.getProblem().getTitle());
        solutionDTO.setUserName(solution.getGithubRepository().getUser().getUsername());
        return solutionDTO;
    }

    private SolutionDetailDTO programmersProblemDetailToDTO(Solution solution) {
        ProgrammersProblemDetail programmersProblemDetail =
                programmersProblemDetailRepository
                        .findByProblem(solution.getProblem())
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        return SolutionDetailDTO.mapToDTO(
                solution,
                programmersProblemDetail.getLink(),
                programmersProblemDetail.getLevel().toString());
    }

    private SolutionDetailDTO baekjoonProblemDetailToDTO(Solution solution) {
        BaekjoonProblemDetail baekjoonProblemDetail =
                baekjoonProblemDetailRepository
                        .findByProblem(solution.getProblem())
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        return SolutionDetailDTO.mapToDTO(
                solution, baekjoonProblemDetail.getLink(), baekjoonProblemDetail.getTier());
    }
}
