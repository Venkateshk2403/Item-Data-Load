package com.item.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom exception for handling service-level errors in the item module.
 * 
 * This exception includes an HTTP status code and a descriptive message,
 * allowing for more flexible and informative error responses.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemServiceException extends RuntimeException{

	/**
     * The HTTP status to be returned with the error.
     */
	private HttpStatus status;

/**
     * A descriptive message explaining the error.
     */
	private String message;
}
