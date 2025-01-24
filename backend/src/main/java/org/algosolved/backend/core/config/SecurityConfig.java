package org.algosolved.backend.core.config;

import lombok.RequiredArgsConstructor;

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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired private final OAuthService oAuthService;

    @Value("${server.servlet.contextPath}")
    private String API_URL_PREFIX; // api

    @Value("${client.base.url}")
    private String clientUrl;

    //    @Autowired private JwtAuthEntryPoint unauthorizedHandler;
    //
    //    @Bean
    //    public JwtFilter authenticationJwtTokenFilter() {
    //        return new JwtFilter();
    //    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable() // CSRF 비활성화 (REST API 사용 시 적합)
                .authorizeHttpRequests() // Spring Security 5.0 이상에서는 authorizeHttpRequests 사용
                .antMatchers(
                        "/api/**",
                        "/api/swagger-ui.html",
                        "/api/swagger-ui/**",
                        "/api/v3/api-docs/**",
                        "/api/v1/user/auth/success")
                .permitAll()
                .antMatchers("/api/v1/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 기반 API는 STATELESS 권장
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/login")
                .and()
                .userInfoEndpoint()
                .userService(oAuthService)
                .and()
                .defaultSuccessUrl(clientUrl);

        // Swagger UI에서 iframe을 사용할 경우에는 아래 설정을 활성화 해야 할 수 있습니다.
        http.headers().frameOptions().sameOrigin(); // Swagger UI와 관련된 문제를 해결하기 위해 사용

        return http.build();
    }
}
