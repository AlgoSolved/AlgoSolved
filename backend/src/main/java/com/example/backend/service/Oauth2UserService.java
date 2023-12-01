package com.example.backend.service;

import com.example.backend.repository.UserRepository;
import com.example.backend.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class Oauth2UserService implements OAuth2UserService {
    @Autowired
    private UserRepository userRepository;
    private HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oauth2User = service.loadUser(userRequest);
        System.out.println(userRepository);

        String username = oauth2User.getAttribute("login");
        if (isExistUser(username)) {
            return oauth2User;
        }
        createFromGithub(oauth2User);
        return oauth2User;
    }

    public boolean isExistUser(String username) {
        // TODO: 유저가 username 을 변경할 수 있으므로 이후 username 이 아니라 다른 걸로 체크해야 함
        // id 로 체크하는 게 좋을 듯
        return userRepository.existsByUsername(username);
    }

    public void createFromGithub(OAuth2User oauth2User) {
        User user = User.builder()
                .username(oauth2User.getAttribute("login"))
                .name(oauth2User.getAttribute("name"))
                .profileImageUrl(oauth2User.getAttribute("avatar_url"))
                .githubUrl(oauth2User.getAttribute("html_url"))
                .build();
        userRepository.save(user);
    }
}
