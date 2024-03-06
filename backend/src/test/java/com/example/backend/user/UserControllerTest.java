package com.example.backend.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
  @DisplayName("id에 해당하는 유저가 존재하는 경우 관련된 정보를 반환한다.")
  @WithMockUser(username = "jake", roles = "USER")
  void 유저_정보_반환_성공() throws Exception {
    UserDTO.Profile userProfile = Instancio.create(UserDTO.Profile.class);
    when(userService.getUserProfile(1L)).thenReturn(userProfile);

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> resultMap = objectMapper.convertValue(userProfile, Map.class);

    mockMvc.perform(get("/v1/user/{id}", 1))
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
  @DisplayName("id에 해당하는 유저가 존재하지 않은 경우 에러를 반환한다.")
  @WithMockUser(username = "jake", roles = "USER")
  void 유저_정보_반환_실패() throws Exception {
    when(userService.getUserProfile(1L))
        .thenThrow(new NotFoundException(ExceptionStatus.NOT_FOUND));

    mockMvc.perform(
            get("/v1/users/{1}", 1)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("사용자가 입력한 유저네임이 일치한 경우 true를 반환한다.")
  @WithMockUser(username = "jake", roles = "USER")
  void 유저_탈퇴_성공() throws Exception {
    mockMvc.perform(delete("/v1/user/{id}", 1)
            .param("inputUsername", "jake"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(
            content()
                .json(
                    """
                        {
                            "code": "2000",
                            "message": "요청에 성공하였습니다."
                        }
                        """
                ));

  }
}
