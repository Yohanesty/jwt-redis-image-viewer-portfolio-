package io.github.yohanesty.viewer.model;

public class ViewerAccessContext {
    private final String userId;
    private final ViewerSession session;

    public ViewerAccessContext(String userId, ViewerSession session) {
        this.userId = userId;
        this.session = session;
    }

    public String getUserId() { return userId; }
    public ViewerSession getSession() { return session; }
}
