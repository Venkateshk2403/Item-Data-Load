package com.item.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for generating BCrypt-hashed passwords.
 * 
 * This class is typically used for generating encoded passwords
 * for initial user setup or testing purposes.
 */

public class PasswordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
	    System.out.println(encoder.encode("venkat@123"));	
	    System.out.println(encoder.encode("tony@123"));
	    System.out.println(encoder.encode("andrew@123"));
	    System.out.println(encoder.encode("jon@123"));
		
	}
}
