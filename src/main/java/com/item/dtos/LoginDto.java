package com.item.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user login requests.
 * 
 * This class holds the credentials required for authentication,
 * allowing users to log in using either a username or an email.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

	/**
     * The username or email used for login.
     */
	private String usernameOrEmail;
	
	/**
     * The user's password.
     */
	private String password;
	
}	
