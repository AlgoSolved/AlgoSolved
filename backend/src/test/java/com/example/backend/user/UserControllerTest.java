package com.example.backend.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.user.controller.UserController;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  @DisplayName("v1/users/{username} 성공 테스트")
  @WithMockUser(username = "jake", roles = "USER")
  void 유저_정보_성공() throws Exception {
    UserDTO userDTO = Instancio.create(UserDTO.class);
    when(userService.getUserByUsername("jake")).thenReturn(userDTO);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> resultMap = objectMapper.convertValue(userDTO, Map.class);

    mockMvc.perform(get("/v1/user/{username}", "jake"))
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
  @DisplayName("v1/users/{username} 실패 테스트")
  @WithMockUser(username = "nonexistentuser", roles = "USER")
  void 유저_정보_실패() throws Exception {

    String nonexistentUsername = "nonexistentuser";
    when(userService.getUserByUsername(nonexistentUsername))
        .thenThrow(new NotFoundException(ExceptionStatus.USER_NOT_FOUND));

    mockMvc.perform(get("/v1/users/{username}", nonexistentUsername)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
