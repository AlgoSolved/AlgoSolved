package org.algosolved.backend.solution;

import static org.assertj.core.api.Assertions.assertThat;

import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.github.repository.GithubRepositoryRepository;
import org.algosolved.backend.problem.domain.BaekjoonProblem;
import org.algosolved.backend.problem.repository.BaekjoonProblemRepository;
import org.algosolved.backend.solution.common.enums.LanguageType;
import org.algosolved.backend.solution.domain.Solution;
import org.algosolved.backend.solution.repository.SolutionRepository;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SolutionRepositoryTest {

    @Autowired SolutionRepository solutionRepository;
    @Autowired UserRepository userRepository;
    @Autowired GithubRepositoryRepository githubRepositoryRepository;
    @Autowired BaekjoonProblemRepository baekjoonProblemRepository;

    @Test
    public void findTop10ByOrderByCreatedAtDescTest() {
        User user =
                User.builder()
                        .username("uchan")
                        .name("uchan")
                        .profileImageUrl("image-url")
                        .githubUrl("github-url")
                        .accessToken("accesstoken")
                        .build();
        userRepository.save(user);
        GithubRepository githubRepository =
                GithubRepository.builder().user(user).repo("repo").build();
        githubRepositoryRepository.save(githubRepository);
        BaekjoonProblem baekjoonProblem =
                BaekjoonProblem.builder()
                        .title("title")
                        .tier("Bronze")
                        .problemNumber(1000L)
                        .build();
        baekjoonProblemRepository.save(baekjoonProblem);

        List<Solution> solutions = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Solution solution =
                    Solution.builder()
                            .githubRepository(githubRepository)
                            .problem(baekjoonProblem)
                            .language(LanguageType.c)
                            .sourceCode("code" + i)
                            .build();
            solutionRepository.save(solution);
            solutions.add(solution);
        }
        List<Solution> result = solutionRepository.findTop10ByOrderByCreatedAtDesc();

        assertThat(result).hasSize(10).containsAll(solutions.subList(1, 11));
    }
}
