package com.example.backend.github.service;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.lib.GithubClient;
import com.example.backend.problem.repository.ProblemRepository;
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
            files = files.stream().filter(file -> file.endsWith(".py")).toList();
            // TODO: solution 파일만 가져오도록 한다.
            files.forEach(
                    file -> {
                        String sourceCode = githubClient.getContent(repo, file);
                        System.out.println(file);
                        System.out.println(sourceCode);
                    });
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
