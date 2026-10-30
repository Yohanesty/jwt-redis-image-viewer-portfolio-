package io.github.yohanesty.viewer.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class ViewerSession {
    private String sessionId;
    private String userId;
    private Set<String> allowedImageIds = new LinkedHashSet<String>();
    private long expiresAtEpochSecond;

    public ViewerSession() {}

    public ViewerSession(String sessionId, String userId,
                         Set<String> allowedImageIds,
                         long expiresAtEpochSecond) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.allowedImageIds = allowedImageIds;
        this.expiresAtEpochSecond = expiresAtEpochSecond;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Set<String> getAllowedImageIds() { return allowedImageIds; }
    public void setAllowedImageIds(Set<String> allowedImageIds) { this.allowedImageIds = allowedImageIds; }

    public long getExpiresAtEpochSecond() { return expiresAtEpochSecond; }
    public void setExpiresAtEpochSecond(long expiresAtEpochSecond) { this.expiresAtEpochSecond = expiresAtEpochSecond; }
}
