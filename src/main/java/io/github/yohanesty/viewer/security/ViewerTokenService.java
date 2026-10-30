package io.github.yohanesty.viewer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ViewerTokenService {
    static final String ISSUER = "portfolio-image-service";
    static final String AUDIENCE = "portfolio-image-viewer";

    private final byte[] secret;

    public ViewerTokenService(String secret) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
    }

    public String issue(String userId, String sessionId, int ttlSeconds) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(userId)
                .claim("sid", sessionId)
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ttlSeconds * 1000L))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public ViewerTokenClaims verify(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .requireIssuer(ISSUER)
                .requireAudience(AUDIENCE)
                .parseClaimsJws(token)
                .getBody();

        String sessionId = claims.get("sid", String.class);
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("sid claim is required");
        }
        return new ViewerTokenClaims(claims.getSubject(), sessionId);
    }
}
