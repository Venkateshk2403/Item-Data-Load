package com.item.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom entry point for handling unauthorized access attempts in JWT-secured
 * endpoints.
 * 
 * This class is triggered when an unauthenticated user tries to access a
 * protected resource. It returns a 401 Unauthorized response with a
 * JSON-formatted error message.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 *Handles unauthorized access by returning a 401 Unauthorized response
	 *with a custom JSON error message.      *      * @param request The
	 *@param response The HTTP response.      * @param
	 * authException The exception thrown when authentication fails.      * @throws
	 * IOException If an input or output error occurs.      * @throws
	 * ServletException If a servlet-specific error occurs.     
	 */

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\":" + authException.getMessage() + "}");
	}

}
