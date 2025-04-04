package org.algosolved.backend.core.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {

        log.error("Unauthorized error. Message - {}", e.getMessage());
        handlerExceptionResolver.resolveException(
                request, response, null, new JwtException(ExceptionStatus.TOKEN_INVALID));
    }
}
