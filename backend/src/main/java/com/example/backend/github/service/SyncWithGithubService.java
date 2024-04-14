package com.example.backend.github.service;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.lib.GithubClient;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.service.SolutionService;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
            List<String[]> result = new ArrayList<>();
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
                Solution solution =
                        solutionService.createSolution(
                                githubRepository, problem, languageType, sourceCode);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
