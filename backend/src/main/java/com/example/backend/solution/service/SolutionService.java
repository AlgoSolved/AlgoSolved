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

        String link = "";
        String rank = "";

        if (problem.getProvider().getName().equals(BaekjoonProblemDetail.class.getSimpleName())) {
            BaekjoonProblemDetail baekjoonProblemDetail =
                    baekjoonProblemDetailRepository
                            .findByProblem(problem)
                            .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

            link = baekjoonProblemDetail.getLink();
            rank = baekjoonProblemDetail.getTier();
        } else {
            ProgrammersProblemDetail programmersProblemDetail =
                    programmersProblemDetailRepository
                            .findByProblem(problem)
                            .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

            link = programmersProblemDetail.getLink();
            rank = programmersProblemDetail.getLevel().toString();
        }

        return SolutionDetailDTO.mapToDTO(solution, link, rank);
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
