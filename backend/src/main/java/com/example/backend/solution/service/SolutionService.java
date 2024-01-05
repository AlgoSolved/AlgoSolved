package com.example.backend.solution.service;

import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.repository.SolutionRepository;
import com.example.backend.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SolutionService {

  private final UserRepository userRepository;

  private final SolutionRepository solutionRepository;

  public List<SolutionDTO> getRecentSolutions() {
    List<Solution> solutions = solutionRepository.findTop10ByOrderByCreatedAtDesc();

    return solutions.stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
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
