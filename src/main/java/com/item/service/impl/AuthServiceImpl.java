package com.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.item.dtos.LoginDto;
import com.item.security.JwtTokenProvider;
import com.item.service.AuthService;

/**
 * Implementation of the AuthService interface.
 * 
 * Handles user authentication by validating credentials and generating JWT tokens.
 */

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
     * Authenticates the user using the provided login credentials and returns a JWT token.
     *
     * @param loginDto the login credentials containing username/email and password
     * @return a JWT token if authentication is successful
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */

	@Override
	public String login(LoginDto loginDto) {
		
	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
	
	Authentication authentication = authenticationManager.authenticate(authToken);
	
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
    String token = jwtTokenProvider.generateToken(authentication);
	
	return token;
	}

}
