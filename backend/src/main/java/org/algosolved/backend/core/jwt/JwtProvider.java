package org.algosolved.backend.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import javax.annotation.PostConstruct;
import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.JwtException;
import org.algosolved.backend.user.dto.UserJwtDto;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private Key key;
    @Value("${jwt.token.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expiration}")
    private int expiration;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String createToken(UserJwtDto userInfo) {

        Claims claims = Jwts.claims();
        claims.put("id", userInfo.getId());
        claims.put("userName", userInfo.getName());
        claims.put("auth", userInfo.getAuthorities());


        DateTime expiryDate = DateTime.now().plusSeconds(expiration);

        return Jwts.builder()
                .setClaims(claims)//
                .setIssuedAt(DateTime.now().toDate())//
                .setExpiration(expiryDate.toDate())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Long getUserId(String token) {
        if (token.startsWith("Bearer ")) {
            token =  token.replace("Bearer ","");
        }
        return Long.valueOf(Jwts.parser().setSigningKey(key).parseClaimsJws(token)
                .getBody().get("id").toString()) ;
    }

    public String getBodyValue(String token, String field) throws Exception {
        if(this.validateJwtToken(token)) {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get(field).toString();
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
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