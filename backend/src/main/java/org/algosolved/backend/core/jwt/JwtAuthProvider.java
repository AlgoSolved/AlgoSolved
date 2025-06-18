package org.algosolved.backend.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import lombok.extern.slf4j.Slf4j;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.enums.JwtType;
import org.algosolved.backend.common.exceptions.JwtException;
import org.algosolved.backend.user.dto.UserJwtDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class JwtAuthProvider {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.token.issr}")
    private String JWT_ISSUER;

    @Value("${jwt.token.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.token.expiration.access}")
    private String ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.token.expiration.refresh}")
    private String REFRESH_TOKEN_EXPIRE_TIME;

    @PostConstruct
    protected void init() {
        if (SECRET_KEY != null) {
            SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        } else {
            logger.warn("SECRET_KEY is null! Check your application.yml");
        }
    }

    public String createToken(UserJwtDto user, JwtType tokenType) {
        Claims claims = Jwts.claims();
        Date now = new Date();
        Date validity = null;

        try {
            if (tokenType.equals(JwtType.ACCESS_TOKEN)) {
                claims.put("id", user.getId());
                claims.put("name", user.getName());
                validity = new Date(now.getTime() + Long.parseLong(ACCESS_TOKEN_EXPIRE_TIME));
            }

            if (tokenType.equals(JwtType.REFRESH_TOKEN)) {
                claims.put("id", user.getId());
                validity = new Date(now.getTime() + Long.parseLong(REFRESH_TOKEN_EXPIRE_TIME));
            }

            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuer(JWT_ISSUER)
                    .setIssuedAt(now)
                    .setExpiration(validity)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();

        } catch (Exception e) { // 토큰 생성 중 에러
            log.error("Error JWT token -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);
        }
    }

    public boolean checkRefreshToken(String refreshToken) throws IOException {
        if (checkRefreshTokenExpired(refreshToken)
                && this.getBodyValue(refreshToken, "iss").equals(JWT_ISSUER)) {
            return true;
        } else {
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);
        }
    }

    public boolean checkRefreshTokenExpired(String refreshToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(refreshToken);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_EXPIRED);
        }
        return true;
    }

    public Object getBodyValue(String token, String field) throws IOException {
        if (this.validateJwtToken(token)) {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .get(field)
                    .toString();
        }
        return null;
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null) {
            log.info("token이 없습니다...");
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);
        }

        if (token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }

        return token;
    }

    public boolean validateJwtToken(String authToken) throws IOException {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;

        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);

        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_EXPIRED);

        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);

        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e.getMessage());
            throw new JwtException(ExceptionStatus.TOKEN_INVALID);
        }
    }
}
