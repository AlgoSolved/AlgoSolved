package com.example.backend.factory;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.user.domain.User;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

public class GithubRepositoryFactory {
    UserFactory userFactory = new UserFactory();

    public GithubRepository createGithubRepository(TestEntityManager testEntityManager) {
        User user = userFactory.createUser(testEntityManager);
        GithubRepository githubRepository =
                GithubRepository.builder().user(user).url("url").token("token").build();
        githubRepository.setCreatedAt(LocalDateTime.now());
        githubRepository.setUpdatedAt(LocalDateTime.now());
        testEntityManager.persist(githubRepository);
        return githubRepository;
    }
}
