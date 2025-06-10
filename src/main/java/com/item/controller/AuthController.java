package com.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.item.dtos.AuthResponse;
import com.item.dtos.LoginDto;
import com.item.service.AuthService;

/**
 * REST controller for handling authentication-related endpoints.
 * 
 * This controller provides an endpoint for user login, which returns a JWT token
 * upon successful authentication.
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;


	/**
     * Authenticates a user and returns a JWT token if credentials are valid.
     * 
     * @param loginDto The login credentials (username and password).
     * @return ResponseEntity containing the JWT token in the response body.
     *         Returns HTTP 200 OK on success.
     */

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
		
		var result = authService.login(loginDto);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwtToken(result);
		return ResponseEntity.ok(authResponse);
	}
}
