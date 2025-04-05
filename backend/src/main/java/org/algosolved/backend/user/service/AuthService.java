package org.algosolved.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.algosolved.backend.common.enums.JwtType;
import org.algosolved.backend.core.jwt.JwtAuthProvider;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.dto.UserJwtDto;
import org.algosolved.backend.user.dto.UserTokenResponseDto.UserSignInResponseDto;
import org.algosolved.backend.user.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtAuthProvider jwtAuthProvider;
    private final UserRepository userRepository;

    // TODO: redis 토큰 저장
    public UserSignInResponseDto loginUser(String username) {
        User user = userRepository.findByUsername(username);
        UserJwtDto userJwtDto = new UserJwtDto(user.getId(), username);
        String accessToken = jwtAuthProvider.createToken(userJwtDto, JwtType.ACCESS_TOKEN);
        String refreshToken =
                jwtAuthProvider.createToken(userJwtDto, JwtType.REFRESH_TOKEN);

        return new UserSignInResponseDto(accessToken, refreshToken);
    }

    private User createUserFromOAuth(String accessToken, OAuth2User oAuth2User) {
        return User.builder()
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("name"))
                .profileImageUrl(oAuth2User.getAttribute("avatar_url"))
                .githubUrl(oAuth2User.getAttribute("html_url"))
                .accessToken(accessToken)
                .build();
    }
}
