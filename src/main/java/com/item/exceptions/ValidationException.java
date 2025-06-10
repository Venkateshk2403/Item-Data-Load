package com.item.exceptions;

/**
 * Custom exception thrown when validation of input data fails.
 * 
 * This exception is typically used to indicate that the provided data
 * does not meet the required business rules.
 */

public class ValidationException extends RuntimeException {
	 public ValidationException() {
		 
	 }
	 public ValidationException(String message) {
		 super(message);
	 }
}
