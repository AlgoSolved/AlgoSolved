package org.algosolved.backend.user.service;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.algosolved.backend.common.enums.Token;
import org.algosolved.backend.core.jwt.JwtAuthenticationProvider;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.dto.UserJwtDto;
import org.algosolved.backend.user.dto.UserTokenResponseDto.UserSignInResponseDto;
import org.algosolved.backend.user.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UserRepository userRepository;
    private final HttpSession session;

    //TODO: redis 토큰 저장
    public UserSignInResponseDto loginUser(String username) {
        User user = userRepository.findByUsername(username);
        UserJwtDto userJwtDto = new UserJwtDto(user.getId(), username);
        String accessToken = jwtAuthenticationProvider.createToken(userJwtDto, Token.ACCESS_TOKEN);
        String refreshToken = jwtAuthenticationProvider.createToken(userJwtDto, Token.REFRESH_TOKEN);

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
