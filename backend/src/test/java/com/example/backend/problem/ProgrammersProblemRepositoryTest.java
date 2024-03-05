package com.example.backend.problem;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.problem.domain.ProgrammersProblem;
import com.example.backend.problem.repository.ProgrammersProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProgrammersProblemRepositoryTest {
    ProgrammersProblem programmersProblem;
    @Autowired
    private ProgrammersProblemRepository programmersProblemRepository;

    @BeforeEach
    void setUp() {
        programmersProblem = ProgrammersProblem.builder().title("title").lessonNumber(1000L).level(1).build();
        programmersProblemRepository.save(programmersProblem);
    }

    @Test
    @DisplayName("findByLessonNumber 메서드 테스트")
    void findByLessonNumberTest() {
        ProgrammersProblem result = programmersProblemRepository.findByLessonNumber(1000L);
        assertThat(result.getId()).isEqualTo(programmersProblem.getId());
    }
}
