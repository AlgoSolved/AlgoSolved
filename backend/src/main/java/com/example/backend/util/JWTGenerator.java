package com.example.backend.util;

import com.google.common.io.Files;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

public class JWTGenerator {
    public static String createJWT(String privateKeyPath, String issuer, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Key signingKey = getPrivateKey(privateKeyPath);

        JwtBuilder builder =
                Jwts.builder()
                        .setIssuedAt(now)
                        .setIssuer(issuer)
                        .signWith(signingKey, signatureAlgorithm);

        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    private static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] keyBytes = Files.toByteArray(new File(filename));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
