package org.algosolved.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.algosolved.backend.lib.GithubClient;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.dto.UserDTO;
import org.algosolved.backend.user.repository.UserRepository;
import org.algosolved.backend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    User user;
    @Mock private GithubClient githubClient;
    @InjectMocks private UserService userService;
    @Mock private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user =
                User.builder()
                        .username("jake")
                        .name("jake")
                        .profileImageUrl("image-url")
                        .githubUrl("github-url")
                        .accessToken("accesstoken")
                        .build();
    }

    @Test
    @DisplayName("id에 해당하는 유저 정보를 반환한다.")
    void 유저_프로필_반환_성공() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO.Profile userProfile = userService.getUserProfile(1L);

        assertEquals("jake", userProfile.getUsername());
        assertEquals("github-url", userProfile.getGithubLink());
        assertEquals(0L, userProfile.getSolutionCount());
    }

    @Test
    @DisplayName("사용자가 입력한 유저네임이 불일치한 경우 false 를 반환한다.")
    void verifyUsernameDiffTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.verifyUsername(1L, "testname");

        assertEquals(false, result);
    }

    @Test
    @DisplayName("사용자가 입력한 유저네임이 일치하는 경우 true 를 반환한다.")
    void verifyUsernameSameTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = userService.verifyUsername(1L, "jake");

        assertEquals(true, result);
    }

    @Test
    @DisplayName("유저를 삭제하고 깃헙 연동을 해제한다.")
    void 유저_탈퇴_성공() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(githubClient.revokeOauthToken("accesstoken")).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
        verify(githubClient, times(1)).revokeOauthToken("accesstoken");
    }
}
