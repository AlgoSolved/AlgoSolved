package com.example.backend.solution;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.factory.SolutionFactory;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // use real database
public class SolutionRepositoryTest {
    @Autowired TestEntityManager testEntityManager;

    @Autowired SolutionRepository solutionRepository;

    SolutionFactory solutionFactory = new SolutionFactory();

    @Test
    public void findTop10ByOrderByCreatedAtDescTest() {
        List<Solution> solutions = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            solutions.add(solutionFactory.createSolution(testEntityManager));
        }
        List<Solution> result = solutionRepository.findTop10ByOrderByCreatedAtDesc();

        assertThat(result).hasSize(10).containsAll(solutions.subList(1, 11));
    }
}
