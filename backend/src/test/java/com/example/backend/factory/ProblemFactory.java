package com.example.backend.factory;

import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.Provider;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

public class ProblemFactory {
    ProviderFactory ProviderFactory = new ProviderFactory();

    public Problem createBaekjoonProblem(TestEntityManager testEntityManager) {
        Provider provider = ProviderFactory.createProvider(testEntityManager);
        Problem problem =
                Problem.builder().title("title").content("content").provider(provider).build();
        problem.setCreatedAt(LocalDateTime.now());
        problem.setUpdatedAt(LocalDateTime.now());
        testEntityManager.persist(problem);
        return problem;
    }
}
