package com.example.backend.factory;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.problem.domain.Problem;
import com.example.backend.solution.domain.Solution;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

public class SolutionFactory {
    GithubRepositoryFactory githubRepositoryFactory = new GithubRepositoryFactory();

    ProblemFactory problemFactory = new ProblemFactory();

    public Solution createSolution(TestEntityManager testEntityManager) {
        GithubRepository githubRepository =
                githubRepositoryFactory.createGithubRepository(testEntityManager);
        Problem problem = problemFactory.createBaekjoonProblem(testEntityManager);

        Solution solution =
                Solution.builder()
                        .githubRepository(githubRepository)
                        .language("language")
                        .sourceCode("sourceCode")
                        .problem(problem)
                        .build();
        solution.setCreatedAt(LocalDateTime.now());
        solution.setUpdatedAt(LocalDateTime.now());
        testEntityManager.persist(solution);
        return solution;
    }
}
