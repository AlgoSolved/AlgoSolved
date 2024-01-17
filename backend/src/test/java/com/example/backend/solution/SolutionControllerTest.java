package com.example.backend.solution;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.solution.controller.SolutionController;
import com.example.backend.solution.dto.SolutionDTO;
import com.example.backend.solution.service.SolutionService;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

@WebMvcTest(SolutionController.class)
public class SolutionControllerTest {
    @Autowired private MockMvc mockMvc;

    @MockBean private SolutionService solutionService;

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
}
