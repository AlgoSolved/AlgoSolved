package com.example.backend.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.repository.BaekjoonProblemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaekjoonProblemRepositoryTest {
    BaekjoonProblem baekjoonProblem;

    @Autowired private BaekjoonProblemRepository baekjoonProblemRepository;

    @BeforeEach
    void setUp() {
        baekjoonProblem =
                BaekjoonProblem.builder()
                        .title("title")
                        .problemNumber(1000L)
                        .tier("Bronze")
                        .build();
        baekjoonProblemRepository.save(baekjoonProblem);
    }

    @Test
    @DisplayName("findByProblemNumber 메서드 테스트")
    void findByProblemNumberTest() {
        BaekjoonProblem result = baekjoonProblemRepository.findByProblemNumber(1000L);
        assertThat(result.getId()).isEqualTo(baekjoonProblem.getId());
    }
}
