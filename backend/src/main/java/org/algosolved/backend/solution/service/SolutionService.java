package org.algosolved.backend.solution.service;

import lombok.RequiredArgsConstructor;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.NotFoundException;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.problem.domain.BaekjoonProblem;
import org.algosolved.backend.problem.domain.Problem;
import org.algosolved.backend.problem.domain.ProgrammersProblem;
import org.algosolved.backend.problem.enums.ProblemType;
import org.algosolved.backend.solution.common.enums.LanguageType;
import org.algosolved.backend.solution.domain.Solution;
import org.algosolved.backend.solution.dto.SolutionDTO;
import org.algosolved.backend.solution.dto.SolutionDetailDTO;
import org.algosolved.backend.solution.repository.SolutionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        solutionDTO.setId(solution.getId());
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
