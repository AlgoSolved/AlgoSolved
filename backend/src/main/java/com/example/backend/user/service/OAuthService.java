package com.example.backend.user.service;

import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            DefaultOAuth2UserService service = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth 서비스에서 가져온 유저 정보 담김

            String username = oAuth2User.getAttribute("login");

            if (isExistUser(username)) {
                return oAuth2User;
            }

            User user = createUserFromOAuth(oAuth2User);

            userRepository.save(user);

            return oAuth2User;
        } catch (OAuth2AuthenticationException e) {
            throw new RuntimeException("[Error] Failed to load OAuth2 user" + e.getMessage(), e);
        }
    }

    public boolean isExistUser(String username) {
        // TODO: 유저가 username 을 변경 시 주의
        return userRepository.existsByUsername(username);
    }

    private User createUserFromOAuth(OAuth2User oAuth2User) {
        return User.builder()
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("name"))
                .profileImageUrl(oAuth2User.getAttribute("avatar_url"))
                .githubUrl(oAuth2User.getAttribute("html_url"))
                .build();
    }
}
