package com.example.backend.controller;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.solution.controller.SolutionController;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.handler.ResponseHandler;
import com.example.backend.solution.service.SolutionService;
import com.example.backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolutionController.class)
public class SolutionControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SolutionService solutionService;

	@MockBean
	private ResponseHandler responseHandler;

	@BeforeEach
	public void setup() {
		User user = User.builder()
			.name("test")
			.username("test")
			.githubUrl("github.com/test")
			.profileImageUrl("github.com/test")
			.build();
		GithubRepository githubRepository = GithubRepository.builder()
			.user(user)
			.url("github.com/test")
			.token(UUID.randomUUID().toString())
			.build();
		Solution solution = Solution.builder()
			.githubRepository(githubRepository)
			.build();

		when(solutionService.getAllSolutions(0, "desc")).thenReturn(Page.empty());
	}

	@Test
	@WithMockUser(username = "jake", roles = "USER")
	public void solutionControllerTest() throws Exception {
		mockMvc.perform(get("/solutions"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(content().json("{\"page\":0,\"totalPages\":0,\"elements\":[]}"));

	}
}
