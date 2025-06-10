package com.item.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for sending authentication responses.
 * 
 * This class encapsulates the JWT token and its type (default: Bearer),
 * which is returned to the client upon successful login.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {

	/**
     * The JWT token issued after successful authentication.
     */

	private String jwtToken;

	/**
     * The token type, typically "Bearer".
     */

	private String type ="Bearer";
}
