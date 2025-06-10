package com.item.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.item.dtos.ItemDto;
import com.item.entity.Item;
import com.item.security.JwtAccessDeniedHandler;
import com.item.security.JwtAuthenticationEntryPoint;
import com.item.security.JwtAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * 
 * - Stateless session management
 * - Password encoding
 * - ModelMapper configuration for DTO-Entity mapping
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	

    /**
     * Constructor-based injection for JWT components.
     * 
     * @param jwtAuthenticationEntryPoint Entry point for unauthorized access.
     * @param jwtAuthenticationFilter JWT filter for request validation.
	 * @param jwtAccessDeniedHandler for denied access
     */

	public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			JwtAuthenticationFilter jwtAuthenticationFilter,JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		super();
		this.jwtAccessDeniedHandler=jwtAccessDeniedHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	

    /**
     * Configures the security filter chain for HTTP requests.
     * 
     * - Disables CSRF
     * - Allows unauthenticated access to `/api/auth/**`
     * - Requires authentication for all other endpoints
     * - Handles unauthorized and access denied exceptions
     * - Adds JWT filter before username-password filter
     * 
     * @param http HttpSecurity object
     * @return Configured SecurityFilterChain
     * @throws Exception if configuration fails
     */

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
	http.csrf(config->config.disable());
		
	http.authorizeHttpRequests(auth->auth
			.requestMatchers("/api/auth/**","/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll().anyRequest().authenticated())
			.exceptionHandling(ex->ex.accessDeniedHandler(jwtAccessDeniedHandler)
					.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
										
		
	return 	http.build();
	

    /**
     * Provides a BCrypt password encoder bean.
     * 
     * @return PasswordEncoder instance
     */

	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
 	/**
     * Provides an AuthenticationManager bean using the given configuration.
     * 
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager instance
     * @throws Exception if retrieval fails
     */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
	}

    /**
     * Configures and returns a ModelMapper bean.
     * 
     * - Skips null values during mapping
     * - Skips setting `itemId` to avoid identifier issues
     * 
     * @return Configured ModelMapper instance
     */

	@Bean
	public ModelMapper modelMapper() {
	    ModelMapper modelMapper = new ModelMapper();
	//    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

	    // Skip itemId during mapping to avoid Hibernate identifier issues
	    modelMapper.typeMap(ItemDto.class, Item.class).addMappings(mapper -> {
	        mapper.skip(Item::setItemId);
	    });

	    return modelMapper;
	}



}
