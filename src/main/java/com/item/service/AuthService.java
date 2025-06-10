package com.item.service;

import com.item.dtos.LoginDto;

/**
 * Service interface for authentication operations.
 * 
 * Defines the contract for user login functionality.
 * Implementations of this interface should handle the logic for validating
 * user credentials and generating JWT tokens.
 */

public interface AuthService {

	/**
     * Authenticates a user based on login credentials and returns a JWT token.
     *
     * @param loginDto The login credentials (username/email and password).
     * @return A JWT token if authentication is successful.
     */

	String login(LoginDto loginDto);
}
