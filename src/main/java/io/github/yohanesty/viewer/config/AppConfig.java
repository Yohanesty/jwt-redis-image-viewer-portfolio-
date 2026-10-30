package io.github.yohanesty.viewer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yohanesty.viewer.security.ViewerTokenService;
import io.github.yohanesty.viewer.service.SyntheticImageService;
import io.github.yohanesty.viewer.service.ViewerAccessService;
import io.github.yohanesty.viewer.store.RedisViewerSessionStore;
import io.github.yohanesty.viewer.store.ViewerSessionStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages = "io.github.yohanesty.viewer")
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool() {
        String host = env("PORTFOLIO_REDIS_HOST", "127.0.0.1");
        int port = Integer.parseInt(env("PORTFOLIO_REDIS_PORT", "6379"));
        return new JedisPool(new JedisPoolConfig(), host, port);
    }

    @Bean
    public ViewerSessionStore viewerSessionStore(JedisPool jedisPool, ObjectMapper objectMapper) {
        return new RedisViewerSessionStore(jedisPool, objectMapper);
    }

    @Bean
    public ViewerTokenService viewerTokenService() {
        String secret = System.getenv("PORTFOLIO_JWT_SECRET");
        if (secret == null || secret.trim().length() < 32) {
            throw new IllegalStateException("PORTFOLIO_JWT_SECRET must be at least 32 characters");
        }
        return new ViewerTokenService(secret);
    }

    @Bean
    public ViewerAccessService viewerAccessService(ViewerTokenService tokenService,
                                                   ViewerSessionStore sessionStore) {
        return new ViewerAccessService(tokenService, sessionStore);
    }

    @Bean
    public SyntheticImageService syntheticImageService() {
        return new SyntheticImageService();
    }

    private String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
