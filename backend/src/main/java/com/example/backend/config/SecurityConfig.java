package com.example.backend.config;

import com.example.backend.service.Oauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private Oauth2UserService oauth2UserService;
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/login")
                .and()
                .userInfoEndpoint()
                .userService(oauth2UserService);
        return http.build();
    }
}