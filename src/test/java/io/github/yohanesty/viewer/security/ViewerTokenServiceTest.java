package io.github.yohanesty.viewer.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewerTokenServiceTest {
    private static final String SECRET =
            "portfolio-test-secret-32-characters-minimum";

    @Test
    public void issueAndVerify() {
        ViewerTokenService service = new ViewerTokenService(SECRET);

        String token = service.issue("demo-user", "demo-session", 300);
        ViewerTokenClaims claims = service.verify(token);

        assertEquals("demo-user", claims.getUserId());
        assertEquals("demo-session", claims.getSessionId());
    }
}
