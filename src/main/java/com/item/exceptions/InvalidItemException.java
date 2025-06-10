package com.item.exceptions;


/**
 * Custom exception thrown when an item fails validation or business rules.
 * 
 * This exception is typically used to indicate that the item data provided
 * is not acceptable for processing or storage.
 */

public class InvalidItemException extends RuntimeException{

	public InvalidItemException() {
		super();
		
	}

	public InvalidItemException(String message) {
		super(message);
		
	}
	
	
}
