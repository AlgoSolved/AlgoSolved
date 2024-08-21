package org.algosolved.backend.solution;

import static org.mockito.Mockito.when;

import net.datafaker.Faker;

import org.algosolved.backend.common.exceptions.NotFoundException;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.problem.domain.Problem;
import org.algosolved.backend.problem.domain.ProgrammersProblem;
import org.algosolved.backend.solution.common.enums.LanguageType;
import org.algosolved.backend.solution.domain.Solution;
import org.algosolved.backend.solution.dto.SolutionDetailDTO;
import org.algosolved.backend.solution.repository.SolutionRepository;
import org.algosolved.backend.solution.service.SolutionService;
import org.algosolved.backend.user.domain.User;
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

    @Nested
    @DisplayName("getSolution 테스트")
    class GetSolutionTest {

        private User user;
        private GithubRepository githubRepository;
        private Problem problem;

        @BeforeEach
        public void setUp() {
            user =
                    User.builder()
                            .username("uchan")
                            .name("uchan")
                            .profileImageUrl("image-url")
                            .githubUrl("github-url")
                            .accessToken("accesstoken")
                            .build();
            githubRepository = GithubRepository.builder().user(user).repo("repo").build();
            problem =
                    ProgrammersProblem.builder()
                            .title(faker.book().title())
                            .lessonNumber(1L)
                            .level(1)
                            .build();
        }

        @Test
        @DisplayName("id에 해당하는 데이터가 있는 경우 관련된 정보를 리턴한다.")
        public void solutionIsExistGetTest() {
            Solution solution =
                    Solution.builder()
                            .githubRepository(githubRepository)
                            .language(LanguageType.c)
                            .problem(problem)
                            .build();
            SolutionDetailDTO expectedSolutionDto = SolutionDetailDTO.mapToDTO(solution);

            when(solutionRepository.findById(solution.getId())).thenReturn(Optional.of(solution));

            SolutionDetailDTO solutionDetailDto = solutionService.getSolution(solution.getId());
            Assertions.assertEquals(
                    expectedSolutionDto.getProblemName(), solutionDetailDto.getProblemName());
            Assertions.assertEquals(
                    expectedSolutionDto.getLanguage(), solutionDetailDto.getLanguage());
            Assertions.assertEquals(
                    expectedSolutionDto.getSourceCode(), solutionDetailDto.getSourceCode());
            Assertions.assertEquals(
                    expectedSolutionDto.getProblemNumber(), solutionDetailDto.getProblemNumber());
            Assertions.assertEquals(expectedSolutionDto.getLink(), solutionDetailDto.getLink());
            Assertions.assertEquals(expectedSolutionDto.getRank(), solutionDetailDto.getRank());
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
