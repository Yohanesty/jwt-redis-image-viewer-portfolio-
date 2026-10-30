package io.github.yohanesty.viewer.model;

public class DemoSessionResponse {
    private final String accessToken;
    private final long expiresInSeconds;

    public DemoSessionResponse(String accessToken, long expiresInSeconds) {
        this.accessToken = accessToken;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getAccessToken() { return accessToken; }
    public long getExpiresInSeconds() { return expiresInSeconds; }
}
