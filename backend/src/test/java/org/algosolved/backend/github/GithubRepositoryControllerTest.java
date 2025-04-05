package org.algosolved.backend.github;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.github.repository.GithubRepositoryRepository;
import org.algosolved.backend.github.service.SyncWithGithubService;
import org.algosolved.backend.mock.WithCustomJwtMockUser;
import org.algosolved.backend.problem.domain.BaekjoonProblem;
import org.algosolved.backend.problem.service.ProblemService;
import org.algosolved.backend.solution.common.enums.LanguageType;
import org.algosolved.backend.solution.domain.Solution;
import org.algosolved.backend.solution.service.SolutionService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class GithubRepositoryControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private SyncWithGithubService syncWithGithubService;
    @MockBean private ProblemService problemService;
    @MockBean private SolutionService solutionService;
    @MockBean private GithubRepositoryRepository githubRepositoryRepository;

    @Nested
    @DisplayName("/v1/github-repository/connect 테스트")
    class ConnectTest {

        @Test
        @DisplayName("성공적으로 연결됐을 때 테스트")
        @WithCustomJwtMockUser(username = "uchan")
        public void connectTest() throws Exception {
            given(syncWithGithubService.connect("uchan", "repo")).willReturn(true);

            mockMvc.perform(
                            post("/v1/github-repositories/connect")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"owner\":\"uchan\",\"repo\":\"repo\"}")
                                    .header("Authorization", "Bearer test")
                                    .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(
                            content()
                                    .json(
                                            """
                          {
                              "code": "2000",
                              "message": "요청에 성공하였습니다.",
                              "data": true
                          }
                          """));
        }

        @Test
        @DisplayName("연결에 실패했을 때 테스트")
        @WithCustomJwtMockUser(username = "uchan")
        public void connectFailTest() throws Exception {
            given(syncWithGithubService.connect("uchan", "repo")).willReturn(false);

            mockMvc.perform(
                            post("/v1/github-repositories/connect")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"owner\":\"uchan\",\"repo\":\"repo\"}")
                                    .header("Authorization", "Bearer test")
                                    .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(
                            content()
                                    .json(
                                            """
                          {
                              "code": "2000",
                              "message": "요청에 성공하였습니다.",
                              "data": false
                          }
                          """));
        }
    }

    @Nested
    @DisplayName("/v1/github-repository/sync 테스트")
    class SyncTest {

        @Test
        @DisplayName("성공적으로 싱크 맞췄을 때 테스트")
        @WithCustomJwtMockUser(username = "uchan")
        public void syncTest() throws Exception {
            String file = "file.py";
            String sourceCode = "sourceCode";
            List<String[]> solutionFiles = new ArrayList<>();
            solutionFiles.add(new String[] {file, sourceCode});

            GithubRepository githubRepository = Instancio.create(GithubRepository.class);
            BaekjoonProblem problem = Instancio.create(BaekjoonProblem.class);
            Solution solution = Instancio.create(Solution.class);

            given(githubRepositoryRepository.findById(1L))
                    .willReturn(java.util.Optional.of(githubRepository));
            given(problemService.getOrCreateFromFile(file)).willReturn(problem);
            given(
                            solutionService.createSolution(
                                    githubRepository, problem, LanguageType.python, sourceCode))
                    .willReturn(solution);

            mockMvc.perform(
                            post("/v1/github-repositories/sync")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", "Bearer test")
                                    .content(
                                            """
                              {
                                  "githubRepositoryId": 1
                              }
                          """)
                                    .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(
                            content()
                                    .json(
                                            """
                          {
                              "code": "2000",
                              "message": "요청에 성공하였습니다.",
                              "data": ""
                          }
                          """));
        }
    }
}
