package com.example.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.backend.common.exceptions.BadRequestException;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.service.UserService;

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

    @InjectMocks private UserService userService;
    @Mock private UserRepository userRepository;

    User user;

    @BeforeEach
    public void setUp() {
        user =
                User.builder()
                        .username("jake")
                        .name("jake")
                        .profileImageUrl("image-url")
                        .githubUrl("github-url")
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
    @DisplayName("사용자가 입력한 유저네임이 불일치한 경우 에러를 반환한다.")
    void 유저_탈퇴_실패() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(
                BadRequestException.class, () -> userService.deleteUser(1L, "incorrectUsername"));
    }
}
