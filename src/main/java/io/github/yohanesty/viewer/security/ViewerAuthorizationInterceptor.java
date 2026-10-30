package io.github.yohanesty.viewer.security;

import io.github.yohanesty.viewer.model.ViewerAccessContext;
import io.github.yohanesty.viewer.service.ViewerAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ViewerAuthorizationInterceptor extends HandlerInterceptorAdapter {
    public static final String ACCESS_CONTEXT =
            ViewerAuthorizationInterceptor.class.getName() + ".ACCESS_CONTEXT";

    private final ViewerAccessService accessService;

    @Autowired
    public ViewerAuthorizationInterceptor(ViewerAccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeUnauthorized(response, "missing bearer token");
            return false;
        }

        try {
            String token = authorization.substring("Bearer ".length()).trim();
            ViewerAccessContext context = accessService.authorize(token);
            request.setAttribute(ACCESS_CONTEXT, context);
            return true;
        } catch (Exception e) {
            writeUnauthorized(response, "invalid viewer token or session");
            return false;
        }
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}
