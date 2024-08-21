package org.algosolved.backend.core.config;

import lombok.RequiredArgsConstructor;

import org.algosolved.backend.core.jwt.JwtAuthEntryPoint;
import org.algosolved.backend.core.jwt.JwtFilter;
import org.algosolved.backend.user.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired private final OAuthService oAuthService;

    @Value("${server.servlet.contextPath}")
    private String API_URL_PREFIX; // api

    @Value("${client.base.url}")
    private String clientUrl;

    @Autowired private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtFilter authenticationJwtTokenFilter() {
        return new JwtFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(
                        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/v1/auth/success")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/login")
                .and()
                .userInfoEndpoint()
                .userService(oAuthService)
                .and()
                .defaultSuccessUrl(clientUrl);

        return http.build();
    }
}
