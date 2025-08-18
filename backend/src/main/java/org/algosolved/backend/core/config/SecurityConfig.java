package org.algosolved.backend.core.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.algosolved.backend.core.filter.JwtAuthenticationFilter;
import org.algosolved.backend.core.filter.OAuthSuccessHandler;
import org.algosolved.backend.core.jwt.JwtAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthTokenFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(
                        cors ->
                                cors.configurationSource(
                                        request -> {
                                            CorsConfiguration config = new CorsConfiguration();
                                            config.setAllowedOrigins(
                                                    List.of("http://localhost:3000", "https://algosolved.org"));
                                            config.setAllowedMethods(
                                                    List.of("GET", "POST", "PUT", "DELETE"));
                                            config.setAllowedHeaders(
                                                    List.of("Authorization", "Content-Type"));
                                            config.setAllowCredentials(true);
                                            return config;
                                        }))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(setAuthRequiredPath())
                .oauth2Login(oauth2 -> oauth2.successHandler(oAuthSuccessHandler))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        (exception) -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(
                        (session) ->
                                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    /** * setAuthRequiredPath Note: 인증이 필요한 필터 경로를 정의한다. */
    private Customizer<
                    AuthorizeHttpRequestsConfigurer<HttpSecurity>
                            .AuthorizationManagerRequestMatcherRegistry>
            setAuthRequiredPath() {
        return new Customizer<
                AuthorizeHttpRequestsConfigurer<HttpSecurity>
                        .AuthorizationManagerRequestMatcherRegistry>() {
            @Override
            public void customize(
                    AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                    .AuthorizationManagerRequestMatcherRegistry
                            registry) {
                registry.antMatchers("/api/v1/**")
                        .authenticated()
                        .antMatchers(
                                "/api/**",
                                "/api/swagger-ui.html",
                                "/api/swagger-ui/**",
                                "/api/v3/api-docs/**",
                                "/api/v1/user/auth/success")
                        .permitAll();
            }
        };
    }
}
