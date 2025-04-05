package org.algosolved.backend.solution;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.algosolved.backend.mock.WithCustomJwtMockUser;
import org.algosolved.backend.solution.dto.SolutionDTO;
import org.algosolved.backend.solution.dto.SolutionDetailDTO;
import org.algosolved.backend.solution.service.SolutionService;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SolutionControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private SolutionService solutionService;

    //인증은 남기지 않고, 테스트마다 초기화한다.
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @WithCustomJwtMockUser
    public void solutionControllerTest() throws Exception {
        // 문제 풀이가 없을 때
        given(solutionService.getRecentSolutions()).willReturn(List.of());

        mockMvc.perform(get("/v1/solutions/recent-list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(
                        content()
                                .json(
                                        """
                                                {
                                                    "code": "2001",
                                                    "message": "최근 문제 풀이가 없습니다.",
                                                    "data": []
                                                }
                                                """));

        // 문제 풀이가 있을 때
        SolutionDTO solutionDto = Instancio.create(SolutionDTO.class);
        given(solutionService.getRecentSolutions()).willReturn(List.of(solutionDto));

        List<Map> elements =
                List.of(
                        Map.of(
                                "problemType", solutionDto.getProblemType(),
                                "problemNumber", solutionDto.getProblemNumber(),
                                "problemName", solutionDto.getProblemName(),
                                "userName", solutionDto.getUserName()));

        mockMvc.perform(get("/v1/solutions/recent-list"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(
                        content()
                                .json(
                                        """
                                                {
                                                    "code": "2000",
                                                    "message": "요청에 성공하였습니다.",
                                                    "data": %s
                                                }
                                                """
                                                .formatted(elements)));
    }

    @Nested
    @DisplayName("/v1/solutions/{id} 테스트")
    class SolutionDetailTest {

        String url = "/v1/solutions/{id}";

        ObjectMapper objectMapper = new ObjectMapper();

        @Test
        @DisplayName("로그인 한 경우")
        @WithCustomJwtMockUser
        public void returnResponseTest() throws Exception {
            SolutionDetailDTO solutionDetailDto = Instancio.create(SolutionDetailDTO.class);

            when(solutionService.getSolution(1L)).thenReturn(solutionDetailDto);

            Map<String, Object> resultMap = objectMapper.convertValue(solutionDetailDto, Map.class);

            mockMvc.perform(get(url, 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(
                            content()
                                    .json(
                                            """
                                                    {
                                                        "code": "2000",
                                                        "message": "요청에 성공하였습니다.",
                                                        "data": %s
                                                    }
                                                    """
                                                    .formatted(resultMap)));
        }

        @Test
        @DisplayName("로그인을 하지 않은 경우")
        public void unAuthorizedUserTest() throws Exception {
            SolutionDetailDTO solutionDetailDto = Instancio.create(SolutionDetailDTO.class);

//            when(solutionService.getSolution(1L)).thenReturn(solutionDetailDto);

            Map<String, Object> resultMap = objectMapper.convertValue(solutionDetailDto, Map.class);

            mockMvc.perform(get(url, 1L)).andExpect(status().isUnauthorized());
        }
    }
}
