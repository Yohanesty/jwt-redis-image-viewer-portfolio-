package io.github.yohanesty.viewer.security;

public class ViewerTokenClaims {
    private final String userId;
    private final String sessionId;

    public ViewerTokenClaims(String userId, String sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() { return userId; }
    public String getSessionId() { return sessionId; }
}
