package com.example.backend.solution;

import static org.mockito.Mockito.when;

import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProgrammersProblemDetail;
import com.example.backend.problem.domain.Provider;
import com.example.backend.problem.repository.ProgrammersProblemDetailRepository;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.dto.SolutionDetailDTO;
import com.example.backend.solution.repository.SolutionRepository;
import com.example.backend.solution.service.SolutionService;

import net.datafaker.Faker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SolutionServiceTest {

    private final Faker faker = new Faker();

    @InjectMocks private SolutionService solutionService;

    @Mock private SolutionRepository solutionRepository;

    @Mock private ProgrammersProblemDetailRepository programmersProblemDetailRepository;

    @Nested
    @DisplayName("getSolution 테스트")
    class GetSolutionTest {

        private Problem problem;

        private ProgrammersProblemDetail programmersProblemDetail;

        @BeforeEach
        public void setUp() {
            Provider provider =
                    Provider.builder().name(ProgrammersProblemDetail.class.getSimpleName()).build();

            problem =
                    Problem.builder()
                            .title(faker.book().title())
                            .content(faker.book().title())
                            .provider(provider)
                            .build();

            programmersProblemDetail =
                    ProgrammersProblemDetail.builder()
                            .problem(problem)
                            .link("test-url")
                            .level(1)
                            .build();
        }

        @Test
        @DisplayName("id에 해당하는 데이터가 있는 경우 관련된 정보를 리턴한다.")
        public void solutionIsExistGetTest() {
            Solution solution =
                    Solution.builder()
                            .language(faker.programmingLanguage().name())
                            .problem(problem)
                            .build();
            SolutionDetailDTO expectedSolutionDto =
                    SolutionDetailDTO.mapToDTO(solution, "test-url", "1");

            when(solutionRepository.findById(solution.getId())).thenReturn(Optional.of(solution));
            when(programmersProblemDetailRepository.findByProblem(problem))
                    .thenReturn(Optional.of(programmersProblemDetail));

            SolutionDetailDTO solutionDetailDto = solutionService.getSolution(solution.getId());
            Assertions.assertEquals(
                    expectedSolutionDto.getProblemName(), solutionDetailDto.getProblemName());
            Assertions.assertEquals(
                    expectedSolutionDto.getLanguage(), solutionDetailDto.getLanguage());
            Assertions.assertEquals(
                    expectedSolutionDto.getSourceCode(), solutionDetailDto.getSourceCode());
        }

        @Test
        @DisplayName("id에 해당하는 솔루션이 없는 경우 에러를 반환한다.")
        public void solutionIsNotExistGetTest() {
            Long id = 1L;
            when(solutionRepository.findById(id)).thenReturn(Optional.empty());

            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> {
                        solutionService.getSolution(id);
                    });
        }
    }
}
