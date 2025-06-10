package com.item.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for sending error responses.
 * 
 * This class encapsulates a simple error message that can be returned
 * to the client when an exception occurs.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ErrorResponse {
	
	/**
     * The error message describing the issue.
     */

	 private String message;
}

