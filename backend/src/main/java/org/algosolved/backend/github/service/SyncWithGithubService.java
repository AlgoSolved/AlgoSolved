package org.algosolved.backend.github.service;

import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.github.repository.GithubRepositoryRepository;
import org.algosolved.backend.lib.GithubClient;
import org.algosolved.backend.problem.domain.Problem;
import org.algosolved.backend.problem.service.ProblemService;
import org.algosolved.backend.solution.common.enums.LanguageType;
import org.algosolved.backend.solution.service.SolutionService;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncWithGithubService {

    private final GithubClient githubClient;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final ProblemService problemService;
    private final SolutionService solutionService;
    private final UserRepository userRepository;

    public boolean connect(String userName, String repositoryName) {
        try {
            User user = userRepository.findByUsername(userName);
            if (user == null) {
                return false;
            }

            if (!githubClient.isPathExist(repositoryName)) {
                return false;
            }

            if (githubRepositoryRepository.existsByRepo(repositoryName)) {
                return false;
            }

            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo(repositoryName).build();
            githubRepositoryRepository.save(githubRepository);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Async
    public void fetch(GithubRepository githubRepository) {
        try {
            String repo = githubRepository.getRepo();

            List<String> solutionFiles =
                    githubClient.getAllFiles(repo).stream()
                            .filter(file -> file.startsWith("백준") || file.startsWith("프로그래머스"))
                            .filter(LanguageType::containsExtension)
                            .toList();

            for (String file : solutionFiles) {
                String sourceCode = githubClient.getContent(repo, file);
                Problem problem = problemService.getOrCreateFromFile(file);
                LanguageType languageType = LanguageType.getLanguageType(file);
                solutionService.createSolution(githubRepository, problem, languageType, sourceCode);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
