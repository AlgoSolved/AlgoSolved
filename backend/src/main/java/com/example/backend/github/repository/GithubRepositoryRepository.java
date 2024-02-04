package com.example.backend.github.repository;

import com.example.backend.github.domain.GithubRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, Long> {

    Boolean existsByRepo(String repo);
}
