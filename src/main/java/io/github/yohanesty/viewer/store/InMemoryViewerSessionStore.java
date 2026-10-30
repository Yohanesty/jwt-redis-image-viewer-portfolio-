package io.github.yohanesty.viewer.store;

import io.github.yohanesty.viewer.model.ViewerSession;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryViewerSessionStore implements ViewerSessionStore {
    private final Map<String, ViewerSession> store = new ConcurrentHashMap<String, ViewerSession>();

    @Override
    public void save(ViewerSession session, int ttlSeconds) {
        store.put(session.getSessionId(), session);
    }

    @Override
    public ViewerSession find(String sessionId) {
        ViewerSession session = store.get(sessionId);
        if (session == null) return null;
        if (session.getExpiresAtEpochSecond() <= Instant.now().getEpochSecond()) {
            store.remove(sessionId);
            return null;
        }
        return session;
    }

    @Override
    public void delete(String sessionId) {
        store.remove(sessionId);
    }
}
