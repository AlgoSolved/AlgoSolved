package org.algosolved.backend.user;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.NotFoundException;
import org.algosolved.backend.mock.WithCustomJwtMockUser;
import org.algosolved.backend.user.dto.UserDto;
import org.algosolved.backend.user.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private UserService userService;

    @Test
    @DisplayName("id에 해당하는 유저가 존재하는 경우 관련된 정보를 반환한다.")
    @WithCustomJwtMockUser
    void 유저_정보_반환_성공() throws Exception {
        UserDto.Profile userProfile = Instancio.create(UserDto.Profile.class);
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
    @WithCustomJwtMockUser
    void 유저_정보_반환_실패() throws Exception {
        when(userService.getUserProfile(1L))
                .thenThrow(new NotFoundException(ExceptionStatus.NOT_FOUND));

        mockMvc.perform(get("/v1/users/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("사용자가 입력한 유저네임이 불일치한 경우 에러를 반환한다.")
    @WithCustomJwtMockUser
    void 유저_탈퇴_실패() throws Exception {
        given(userService.deleteUser(1L)).willReturn(false);
        mockMvc.perform(
                        delete("/v1/user/{id}", 1)
                                .param("inputUsername", "incorrectUsername")
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("성공적으로 탈퇴한 경우 true를 반환한다.")
    @WithCustomJwtMockUser
    void 유저_탈퇴_성공() throws Exception {
        given(userService.verifyUsername(1L, "jake")).willReturn(true);
        given(userService.deleteUser(1L)).willReturn(true);

        mockMvc.perform(delete("/v1/user/{id}", 1).param("inputUsername", "jake").with(csrf()))
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
                        """));
    }
}
