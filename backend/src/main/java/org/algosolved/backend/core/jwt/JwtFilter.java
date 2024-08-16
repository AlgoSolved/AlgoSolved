package org.algosolved.backend.core.jwt;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.NotFoundException;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired private JwtProvider jwtProvider;

    @Autowired private UserRepository userRepository;

    @Value("${server.servlet.contextPath}")
    private String API_URL_PREFIX; // api

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // TODO: 토큰 검증을 제외할 path -> Aspect로 빼낼지 고민
        String[] equalsWith = {API_URL_PREFIX + "/login", "/", "/ping"};

        boolean equalsWithPass =
                Arrays.asList(equalsWith).stream().anyMatch(url -> uri.equals(url));

        if (equalsWithPass) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        if (token != null && jwtProvider.validateJwtToken(token)) {

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(jwtProvider.getBodyValue(token, "auth")));

            Optional<User> user =
                    userRepository.findById(Long.valueOf(jwtProvider.getBodyValue(token, "id")));

            if (user.isEmpty()) {
                throw new NotFoundException(ExceptionStatus.NOT_FOUND);
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);

            request.setAttribute("Authentication", token);
            response.setHeader("Authentication", token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            return;
        }

        log.info("JWT Token Authenticated");
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null) return null;

        if (authorization.startsWith("Bearer ")) {
            return authorization.replace("Bearer ", "");
        }

        return authorization;
    }
}
