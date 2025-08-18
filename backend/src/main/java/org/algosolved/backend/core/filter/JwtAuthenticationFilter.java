package org.algosolved.backend.core.filter;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.algosolved.backend.common.exceptions.JwtException;
import org.algosolved.backend.core.jwt.JwtAuthProvider;
import org.algosolved.backend.user.dto.UserJwtDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtAuthProvider jwtAuthProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String uri = request.getRequestURI();

        log.info("[url]" + uri);
        String method = request.getMethod();
        // 다음과 같은 uri는 토큰검증x
        String[] equalsWith = {
            "/api/health/ping",
            "/api/v1/users/signin",
            "/api/v1/users/refresh",
            "/api/oauth2/authorization/github",
            "/api/v1/user/auth/success",
            "/api/v1/solutions/recent-list",
            "/",
            "/error"
        };

        String[] startWith = {};
        String[] endWith = {};

        // OPTIONS 는 토큰 검증 X
        boolean isPreflight = method.equals("OPTIONS");
        boolean equalsWithPass = equalsWithUri(uri, equalsWith);
        boolean startWithPass = startsWithUri(uri, startWith);
        boolean endWithPass = endWithUri(uri, endWith);

        // 토큰 검증이 필요없는 건은 해당 필터를 통과함.
        if (equalsWithPass || startWithPass || endWithPass || isPreflight) {
            log.info("filter Pass! : {}", uri);
            filterChain.doFilter(request, response);
            return;
        }

        log.info("doFilterInternal");
        log.info("[url]" + uri);

        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = jwtAuthProvider.getToken(request);

            log.info(token);

            if (jwtAuthProvider.validateJwtToken(token)) {
                // 유저 권한 파싱
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(
                        new SimpleGrantedAuthority(
                                (String) jwtAuthProvider.getBodyValue(token, "roleCode")));

                // payload 파싱
                UserJwtDto userInfo =
                        UserJwtDto.builder()
                                .id(
                                        Long.parseLong(
                                                jwtAuthProvider
                                                        .getBodyValue(token, "id")
                                                        .toString()))
                                .name(jwtAuthProvider.getBodyValue(token, "name").toString())
                                .build();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException e) { // 토큰 만료
            log.error("Expired Token -> Message: {}", e.getMessage());

        } catch (JwtException e) {
            log.error(e.getMessage());

            handlerExceptionResolver.resolveException(request, response, null, e);
            return;

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

        log.info("Authenticate Success");
        filterChain.doFilter(request, response);
    }

    private boolean equalsWithUri(String uri, String[] list) {
        return Arrays.asList(list).contains(uri);
    }

    private boolean startsWithUri(String uri, String[] list) {
        return Arrays.stream(list).anyMatch(uri::startsWith);
    }

    private boolean endWithUri(String uri, String[] list) {
        return Arrays.stream(list).anyMatch(uri::endsWith);
    }
}
