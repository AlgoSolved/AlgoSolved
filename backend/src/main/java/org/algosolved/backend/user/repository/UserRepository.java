package org.algosolved.backend.user.repository;

import org.algosolved.backend.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    User findByUsername(String username);

    Optional<User> findById(Long id);
}
