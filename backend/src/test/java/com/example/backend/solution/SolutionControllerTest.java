package com.example.backend.solution;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.solution.controller.SolutionController;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.dto.SolutionDetailDTO;
import com.example.backend.solution.service.SolutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
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

@WebMvcTest(SolutionController.class)
public class SolutionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolutionService solutionService;

    @Test
    @WithMockUser(username = "jake", roles = "USER")
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
                                "problemProvider", solutionDto.getProblemProvider(),
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
        @WithMockUser(username = "jake", roles = "USER")
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
        @DisplayName("로그인 하지 않은 경우")
        public void invalidParamsTest() throws Exception {
            SolutionDetailDTO solutionDetailDto = Instancio.create(SolutionDetailDTO.class);

            when(solutionService.getSolution(1L)).thenReturn(solutionDetailDto);

            Map<String, Object> resultMap = objectMapper.convertValue(solutionDetailDto, Map.class);

            mockMvc.perform(get(url, 1L))
                    .andExpect(status().is3xxRedirection());

        }
    }
}
