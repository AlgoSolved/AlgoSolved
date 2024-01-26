package com.example.backend.factory;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.user.domain.User;

import net.datafaker.Faker;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

public class GithubRepositoryFactory {
    Faker faker = new Faker();
    UserFactory userFactory = new UserFactory();

    public GithubRepository createGithubRepository(TestEntityManager testEntityManager) {
        User user = userFactory.createUser(testEntityManager);
        GithubRepository githubRepository =
                GithubRepository.builder().user(user).repo(faker.name().lastName()).build();
        githubRepository.setCreatedAt(LocalDateTime.now());
        githubRepository.setUpdatedAt(LocalDateTime.now());
        testEntityManager.persist(githubRepository);
        return githubRepository;
    }
}
