package com.example.backend.github;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.github.controller.GithubRepositoryController;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.service.SyncWithGithubService;
import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.service.SolutionService;
import java.util.ArrayList;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GithubRepositoryController.class)
public class GithubRepositoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SyncWithGithubService syncWithGithubService;
  @MockBean
  private ProblemService problemService;
  @MockBean
  private SolutionService solutionService;
  @MockBean
  private GithubRepositoryRepository githubRepositoryRepository;

  @Nested
  @DisplayName("/v1/github-repository/connect 테스트")
  class ConnectTest {

    @Test
    @WithMockUser(username = "uchan", roles = "USER")
    @DisplayName("성공적으로 연결됐을 때 테스트")
    public void connectTest() throws Exception {
      given(syncWithGithubService.connect("uchan", "repo")).willReturn(true);

      mockMvc.perform(
              post("/v1/github-repositories/connect")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"owner\":\"uchan\",\"repo\":\"repo\"}")
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
    @WithMockUser(username = "uchan", roles = "USER")
    @DisplayName("연결에 실패했을 때 테스트")
    public void connectFailTest() throws Exception {
      given(syncWithGithubService.connect("uchan", "repo")).willReturn(false);

      mockMvc.perform(
              post("/v1/github-repositories/connect")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"owner\":\"uchan\",\"repo\":\"repo\"}")
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
    @WithMockUser(username = "uchan", roles = "USER")
    @DisplayName("성공적으로 싱크 맞췄을 때 테스트")
    public void syncTest() throws Exception {
      String file = "file.py";
      String sourceCode = "sourceCode";
      List<String[]> solutionFiles = new ArrayList<>();
      solutionFiles.add(new String[]{file, sourceCode});

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
