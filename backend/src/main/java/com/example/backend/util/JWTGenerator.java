package com.example.backend.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.StringReader;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Date;

public class JWTGenerator {
    public static String createJWT(String privateKey, String issuer, long ttlMillis)
            throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Key signingKey = getPrivateKey(privateKey);

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

    private static PrivateKey getPrivateKey(String pemContent) throws Exception {
        PEMParser pemParser = new PEMParser(new StringReader(pemContent));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((org.bouncycastle.openssl.PEMKeyPair) object);
        pemParser.close();
        return kp.getPrivate();
    }
}
