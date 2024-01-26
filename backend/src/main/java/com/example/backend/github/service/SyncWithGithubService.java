package com.example.backend.github.service;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.lib.GithubClient;
import com.example.backend.problem.repository.ProblemRepository;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncWithGithubService {
    private final GithubClient githubClient = new GithubClient();
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;

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

    public boolean fetch(Long githubRepositoryId) {
        try {
            GithubRepository githubRepository =
                    githubRepositoryRepository.findById(githubRepositoryId).get();
            String repo = githubRepository.getRepo();

            List<String> files = githubClient.getAllFiles(repo);
            files.forEach(
                    file -> {
                        // TODO: solution 파일 이름 정규식으로 확인할 것
                        if (file.endsWith("main.py")) {
                            String sourceCode = githubClient.getContent(repo, file);
                            Solution solution =
                                    Solution.builder()
                                            .githubRepository(githubRepository)
                                            .language("python")
                                            .sourceCode(sourceCode)
                                            .problem(
                                                    problemRepository
                                                            .findById(2L)
                                                            .get()) // dummy problem
                                            .build();
                            solutionRepository.save(solution);
                        }
                    });
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
