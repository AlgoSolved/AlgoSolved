package org.algosolved.backend.core.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OAuth2AuthenticationFilter
        extends org.springframework.web.filter.OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            javax.servlet.FilterChain filterChain)
            throws java.io.IOException, javax.servlet.ServletException {
        Authentication authentication = (Authentication) request.getUserPrincipal();

        if (authentication != null && authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            request.getSession().setAttribute("user", oauthToken.getPrincipal()); // 세션에 사용자 정보 저장
        }

        filterChain.doFilter(request, response);
    }
}
