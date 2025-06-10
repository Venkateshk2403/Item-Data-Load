package com.item.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.item.dtos.ErrorResponse;
import com.item.exceptions.InvalidItemException;
import com.item.exceptions.ItemNotFoundException;
import com.item.exceptions.ValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

 /**
 * Global exception handler for the application.
 * 
 * This class handles various exceptions thrown across the application and returns
 * appropriate HTTP responses with user-friendly error messages.
 */
	
@ControllerAdvice
public class ErrorHandler {

     /**
     * Handles cases where an item is not found.
     * 
     * @param ex ItemNotFoundException
     * @return 404 NOT FOUND with error message
     */

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleItemNotFound(ItemNotFoundException ex) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
    /**
     * Handles cases where an item is invalid.
     * 
     * @param ex InvalidItemException
     * @return 406 NOT ACCEPTABLE with error message
     */

	@ExceptionHandler(InvalidItemException.class)
	public ResponseEntity<ErrorResponse> handleInvalidItem(InvalidItemException ex) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	

    /**
     * Handles validation errors for request bodies.
     * 
     * @param ex MethodArgumentNotValidException
     * @return 400 BAD REQUEST with field-specific error messages
     */

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

		StringBuilder sb = new StringBuilder();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {

			sb.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + " ");

		}
		ErrorResponse errorResponse = new ErrorResponse(sb.toString());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
//	

	/**
     * Handles authorization denial errors.
     * 
     * @param ex AuthorizationDeniedException
     * @return 403 FORBIDDEN with timestamp and error details
     */

//	@ExceptionHandler(AuthorizationDeniedException.class)
// public ResponseEntity<Object> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
//    Map<String, Object> response = new HashMap<>();
//    response.put("timestamp", LocalDateTime.now());
//    response.put("status", HttpStatus.FORBIDDEN.value());
//    response.put("error", "Forbidden");
//    response.put("message", ex.getMessage());
//
//   return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); 
//	}
	

    /**
     * Handles custom validation exceptions.
     * 
     * @param ex ValidationException
     * @return 400 BAD REQUEST with error message
     */

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex){
		
		ErrorResponse errorResponse=new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());
		
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
		
}

/**
 * Handles validation errors triggered by constraint violations.
 *
 * is thrown, typically during validation of entity fields or method parameters.
 * It extracts all constraint violations, maps each field to its corresponding error message,
 * and returns a structured response with HTTP 400 (Bad Request).
 *
 * @param ex the ConstraintViolationException containing validation errors
 * @return a ResponseEntity with a map of field names and error messages, and HTTP 400 status
 */

	  @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
	        Map<String, String> errors = new HashMap<>();

	        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
	        for (ConstraintViolation<?> violation : violations) {
	            String propertyPath = violation.getPropertyPath().toString();
	            String message = violation.getMessage();
	            errors.put(propertyPath, message);
	        }
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }

/**
 * Handles errors caused by malformed or unreadable JSON input.
 *
 * is thrown, typically due to invalid JSON syntax or incorrect data types in the request body.
 * It extracts the most specific cause of the error and returns a plain text message
 * with HTTP 400 (Bad Request) status.
 *
 * @param ex the HttpMessageNotReadableException containing parsing error details
 * @return a ResponseEntity with an error message and HTTP 400 status
 */

	  
	  @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<String> handleJsonParseError(HttpMessageNotReadableException ex) {
	        return new ResponseEntity<>("Invalid JSON input : " + ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
	    }
 

}
