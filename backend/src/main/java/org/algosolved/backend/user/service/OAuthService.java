package org.algosolved.backend.user.service;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            DefaultOAuth2UserService service = new DefaultOAuth2UserService();
            String accessToken = userRequest.getAccessToken().getTokenValue();
            OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth 서비스에서 가져온 유저 정보 담김

            System.out.println(oAuth2User.getAttributes());

            String username = oAuth2User.getAttribute("login").toString();
            System.out.println(oAuth2User.getAttribute("login").toString());

            User user = createUserFromOAuth(accessToken, oAuth2User);;

            if (!isExistUser(username)) {
                userRepository.save(user);
            } else {
                user = userRepository.findByUsername(username);
            }

            if (user == null) {
                System.out.println("유저가 null이다.");
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, oAuth2User.getAuthorities());

            System.out.println(authentication+" : authentication");
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return oAuth2User;
        } catch (OAuth2AuthenticationException e) {
            throw new RuntimeException("[Error] Failed to load OAuth2 user" + e.getMessage(), e);
        }
    }

    public boolean isExistUser(String username) {
        // TODO: 유저가 username 을 변경 시 주의
        return userRepository.existsByUsername(username);
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
