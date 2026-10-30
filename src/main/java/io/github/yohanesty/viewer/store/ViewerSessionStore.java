package io.github.yohanesty.viewer.store;

import io.github.yohanesty.viewer.model.ViewerSession;

public interface ViewerSessionStore {
    void save(ViewerSession session, int ttlSeconds);
    ViewerSession find(String sessionId);
    void delete(String sessionId);
}
