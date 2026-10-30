package io.github.yohanesty.viewer.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yohanesty.viewer.model.ViewerSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisViewerSessionStore implements ViewerSessionStore {
    private static final String KEY_PREFIX = "portfolio:viewer-session:";

    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public RedisViewerSessionStore(JedisPool jedisPool, ObjectMapper objectMapper) {
        this.jedisPool = jedisPool;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(ViewerSession session, int ttlSeconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = objectMapper.writeValueAsString(session);
            jedis.setex(key(session.getSessionId()), ttlSeconds, json);
        } catch (Exception e) {
            throw new IllegalStateException("failed to save viewer session", e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    @Override
    public ViewerSession find(String sessionId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = jedis.get(key(sessionId));
            return json == null ? null : objectMapper.readValue(json, ViewerSession.class);
        } catch (Exception e) {
            throw new IllegalStateException("failed to read viewer session", e);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    @Override
    public void delete(String sessionId) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key(sessionId));
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    private String key(String sessionId) {
        return KEY_PREFIX + sessionId;
    }
}
