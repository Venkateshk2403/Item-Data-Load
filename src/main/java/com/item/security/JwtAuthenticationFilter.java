package com.item.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.item.exceptions.ItemServiceException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * JWT Authentication Filter that intercepts HTTP requests to validate JWT tokens.
 * 
 * This filter:
 * - Extracts the token from the Authorization header.
 * - Validates the token using JwtTokenProvider.
 * - Loads user details and sets the authentication in the security context.
 * - Handles token-related exceptions and returns a 401 Unauthorized response if needed.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {
	
	private JwtTokenProvider jwtTokenProvider;
	
	private UserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	/**
     * Filters each request to validate JWT and set authentication context.
     * 
     * @param request HTTP request
     * @param response HTTP response
     * @param filterChain Filter chain
     * @throws ServletException
     * @throws IOException
     */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		String token = getTokenFromRequest(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			
			String username =jwtTokenProvider.getUsername(token);
			
			UserDetails userDetails= userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		}catch(ItemServiceException e) {
			  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	           response.setContentType("application/json");
	           response.getWriter().write("{\"error\":" + e.getMessage() + "}");
	           return;
		}
		
		filterChain.doFilter(request, response);
	}
	

	/**
     * Extracts the JWT token from the Authorization header.
     * 
     * @param request HTTP request
     * @return JWT token string or null if not present
     */

	private String getTokenFromRequest(HttpServletRequest request) {
		
		String bearertoken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearertoken) && bearertoken.startsWith("Bearer")) {
			
			String token = bearertoken.substring(7);
			
			return token;
		}
		return null;
	}
}
