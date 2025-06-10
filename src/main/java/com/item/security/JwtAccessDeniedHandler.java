package com.item.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Custom handler for access denied exceptions in JWT-secured endpoints.
 * 
 * This class is triggered when an authenticated user tries to access a resource
 * they do not have permission for. It returns a 403 Forbidden response with a
 * JSON-formatted error message instead of the default HTML error page.
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler
{	
	

/**
     * Handles access denied exceptions by returning a 403 Forbidden response
     * with a custom JSON error message.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param accessDeniedException The exception thrown when access is denied.
     * @throws IOException If an input or output error occurs.
     * @throws ServletException If a servlet-specific error occurs.
     */

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        System.out.println("Handling AccessDeniedException");
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Access denied\"}");
        response.getWriter().flush();
    }
}

