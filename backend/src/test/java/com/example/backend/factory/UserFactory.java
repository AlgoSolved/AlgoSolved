package com.example.backend.factory;

import com.example.backend.user.domain.User;

import net.datafaker.Faker;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

public class UserFactory {
    Faker faker = new Faker();

    public User createUser(TestEntityManager testEntityManager) {
        User user =
                User.builder()
                        .username(faker.unique().fetchFromYaml("name.last_name"))
                        .name(faker.name().fullName())
                        .profileImageUrl("profileImageUrl")
                        .githubUrl("githubUrl")
                        .build();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        testEntityManager.persist(user);
        return user;
    }
}
