package com.item.exceptions;

/**
 * Custom exception thrown when an item with the specified ID is not found.
 * 
 * This exception is typically used in service or controller layers to indicate
 * that a requested item does not exist in the database.
 */

public class ItemNotFoundException extends RuntimeException{

	public ItemNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
