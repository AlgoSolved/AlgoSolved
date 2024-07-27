package org.algosolved.backend.github.repository;

import org.algosolved.backend.github.domain.GithubRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubRepositoryRepository extends JpaRepository<GithubRepository, Long> {

    Boolean existsByRepo(String repo);
}
