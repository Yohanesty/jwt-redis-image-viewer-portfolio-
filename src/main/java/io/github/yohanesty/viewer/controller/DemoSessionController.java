package io.github.yohanesty.viewer.controller;

import io.github.yohanesty.viewer.model.DemoSessionRequest;
import io.github.yohanesty.viewer.model.DemoSessionResponse;
import io.github.yohanesty.viewer.model.ViewerSession;
import io.github.yohanesty.viewer.security.ViewerTokenService;
import io.github.yohanesty.viewer.store.ViewerSessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.UUID;

@RestController
@RequestMapping("/api/demo")
public class DemoSessionController {
    private static final int SAMPLE_TTL_SECONDS = 300;

    private final ViewerTokenService tokenService;
    private final ViewerSessionStore sessionStore;

    @Autowired
    public DemoSessionController(ViewerTokenService tokenService,
                                 ViewerSessionStore sessionStore) {
        this.tokenService = tokenService;
        this.sessionStore = sessionStore;
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public DemoSessionResponse create(@RequestBody DemoSessionRequest request) {
        if (request.getUserId() == null || request.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("userId is required");
        }
        if (request.getImageIds() == null || request.getImageIds().isEmpty()) {
            throw new IllegalArgumentException("imageIds are required");
        }

        String sessionId = UUID.randomUUID().toString();
        long expiresAt = Instant.now().getEpochSecond() + SAMPLE_TTL_SECONDS;

        ViewerSession session = new ViewerSession(
                sessionId,
                request.getUserId(),
                new LinkedHashSet<String>(request.getImageIds()),
                expiresAt
        );

        sessionStore.save(session, SAMPLE_TTL_SECONDS);
        String token = tokenService.issue(request.getUserId(), sessionId, SAMPLE_TTL_SECONDS);

        return new DemoSessionResponse(token, SAMPLE_TTL_SECONDS);
    }
}
