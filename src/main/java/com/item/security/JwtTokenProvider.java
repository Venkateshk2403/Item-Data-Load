package com.item.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.item.exceptions.ItemServiceException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


/**
 * Utility class for handling JWT operations such as token generation,
 * validation, and extracting user information.
 */

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	@Value("${app.jwt.expiry-millis}")
	private long expiryMillis;

	/**
     * Generates a JWT token for the authenticated user.
     *
     * @param authentication The authentication object containing user details.
     * @return A signed JWT token string.
     */

	public String generateToken(Authentication authentication) {

		String username=authentication.getName();

		Date currentDate=new Date();

		Date expiryDate=new Date(currentDate.getTime()+expiryMillis);

		String token=
				Jwts.builder()
				.subject(username)
				.issuedAt(currentDate)
				.expiration(expiryDate)
				.signWith(key())
				.compact();

		return token;

	}
	/**
     * Returns the signing key used for JWT operations.
     *
     * @return A SecretKey derived from the base64-encoded secret.
     */

	private Key key() {

		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	/**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username embedded in the token.
     */

	public String getUsername(String token) {
		String username=Jwts.parser()
		.verifyWith((SecretKey) key())
		.build()
		.parseSignedClaims(token)
		.getPayload()
		.getSubject();

		return username;
	}

	/**
     * Validates the JWT token for structure, signature, and expiration.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid; otherwise, throws an exception.
     * @throws ItemServiceException with appropriate HTTP status and message.
     */

	public boolean validateToken(String token) {

		try {

			Jwts.parser().verifyWith((SecretKey)key())
			.build().parse(token);

			return true;
		}
		catch(MalformedJwtException  | io.jsonwebtoken.security.SignatureException ex) {
			throw new ItemServiceException(HttpStatus.BAD_REQUEST,"Invalid token");

		}
		catch(ExpiredJwtException ex) {
			throw new ItemServiceException(HttpStatus.BAD_REQUEST,"Expired Token");

		}
		catch(UnsupportedJwtException ex) {
			throw new ItemServiceException(HttpStatus.BAD_REQUEST,"Unspported Token");

		}
		catch(IllegalArgumentException ex) {
			throw new ItemServiceException(HttpStatus.BAD_REQUEST,"Illegal String claim is null/empty");


		}
	}
}
