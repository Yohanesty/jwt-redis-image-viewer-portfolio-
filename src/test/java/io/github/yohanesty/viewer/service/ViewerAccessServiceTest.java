package io.github.yohanesty.viewer.service;

import io.github.yohanesty.viewer.model.ViewerAccessContext;
import io.github.yohanesty.viewer.model.ViewerSession;
import io.github.yohanesty.viewer.security.ViewerTokenService;
import io.github.yohanesty.viewer.store.InMemoryViewerSessionStore;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public class ViewerAccessServiceTest {
    private static final String SECRET =
            "portfolio-test-secret-32-characters-minimum";

    @Test
    public void jwtAndSessionMustMatch() {
        ViewerTokenService tokenService = new ViewerTokenService(SECRET);
        InMemoryViewerSessionStore store = new InMemoryViewerSessionStore();

        ViewerSession session = new ViewerSession(
                "sid-1",
                "demo-user",
                new LinkedHashSet<String>(Arrays.asList("IMG-DEMO-001")),
                Instant.now().getEpochSecond() + 300
        );
        store.save(session, 300);

        ViewerAccessService service = new ViewerAccessService(tokenService, store);
        ViewerAccessContext context =
                service.authorize(tokenService.issue("demo-user", "sid-1", 300));

        assertEquals("demo-user", context.getUserId());
        service.assertImageAllowed(context, "IMG-DEMO-001");
    }

    @Test(expected = ForbiddenException.class)
    public void rejectsImageOutsideSession() {
        ViewerTokenService tokenService = new ViewerTokenService(SECRET);
        InMemoryViewerSessionStore store = new InMemoryViewerSessionStore();

        ViewerSession session = new ViewerSession(
                "sid-2",
                "demo-user",
                new LinkedHashSet<String>(Arrays.asList("IMG-DEMO-001")),
                Instant.now().getEpochSecond() + 300
        );
        store.save(session, 300);

        ViewerAccessService service = new ViewerAccessService(tokenService, store);
        ViewerAccessContext context =
                service.authorize(tokenService.issue("demo-user", "sid-2", 300));

        service.assertImageAllowed(context, "IMG-NOT-ALLOWED");
    }
}
