package io.github.yohanesty.viewer.service;

import io.github.yohanesty.viewer.model.ViewerAccessContext;
import io.github.yohanesty.viewer.model.ViewerSession;
import io.github.yohanesty.viewer.security.ViewerTokenClaims;
import io.github.yohanesty.viewer.security.ViewerTokenService;
import io.github.yohanesty.viewer.store.ViewerSessionStore;

import java.time.Instant;

public class ViewerAccessService {
    private final ViewerTokenService tokenService;
    private final ViewerSessionStore sessionStore;

    public ViewerAccessService(ViewerTokenService tokenService,
                               ViewerSessionStore sessionStore) {
        this.tokenService = tokenService;
        this.sessionStore = sessionStore;
    }

    public ViewerAccessContext authorize(String token) {
        ViewerTokenClaims claims = tokenService.verify(token);
        ViewerSession session = sessionStore.find(claims.getSessionId());

        if (session == null) {
            throw new UnauthorizedException("viewer session not found");
        }
        if (!claims.getUserId().equals(session.getUserId())) {
            throw new UnauthorizedException("token subject and session user mismatch");
        }
        if (session.getExpiresAtEpochSecond() <= Instant.now().getEpochSecond()) {
            throw new UnauthorizedException("viewer session expired");
        }
        return new ViewerAccessContext(claims.getUserId(), session);
    }

    public void assertImageAllowed(ViewerAccessContext context, String imageId) {
        if (context.getSession().getAllowedImageIds() == null
                || !context.getSession().getAllowedImageIds().contains(imageId)) {
            throw new ForbiddenException("image is not allowed in this viewer session");
        }
    }
}
