package com.example.backend.factory;

import com.example.backend.problem.domain.Provider;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class ProviderFactory {
    public Provider createProvider(TestEntityManager testEntityManager) {
        Provider provider = Provider.builder().name("baekjoon").build();
        testEntityManager.persist(provider);
        return provider;
    }
}
