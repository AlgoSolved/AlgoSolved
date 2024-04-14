package com.example.backend.solution.service;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProgrammersProblem;
import com.example.backend.problem.enums.ProblemType;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.dto.SolutionDetailDTO;
import com.example.backend.solution.repository.SolutionRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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

        Problem problem = solution.getProblem();

        if (problem == null) {
            throw new NotFoundException(ExceptionStatus.NOT_FOUND);
        }

        return SolutionDetailDTO.mapToDTO(solution);
    }

    @Transactional
    public Solution createSolution(
            GithubRepository githubRepository,
            Problem problem,
            LanguageType languageType,
            String sourceCode) {
        Solution solution =
                Solution.builder()
                        .githubRepository(githubRepository)
                        .language(languageType)
                        .sourceCode(sourceCode)
                        .problem(problem)
                        .build();
        String hashKey = solution.generateHashKey();
        if (!solutionRepository.existsByHashKey(hashKey)) {
            solutionRepository.save(solution);
            return solution;
        }
        return null;
    }

    private SolutionDTO mapToDTO(Solution solution) {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setProblemType(getProblemType(solution.getProblem()));
        solutionDTO.setProblemNumber(solution.getProblem().getId().toString());
        solutionDTO.setProblemName(solution.getProblem().getTitle());
        solutionDTO.setUserName(solution.getGithubRepository().getUser().getUsername());
        return solutionDTO;
    }

    private String getProblemType(Problem problem) {
        if (problem instanceof BaekjoonProblem) {
            return ProblemType.BaekjoonProblem.name();
        } else if (problem instanceof ProgrammersProblem) {
            return ProblemType.ProgrammersProblem.name();
        } else {
            throw new IllegalArgumentException("Unknown problem type");
        }
    }
}
