package com.example.backend.solution;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.repository.BaekjoonProblemRepository;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;

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
