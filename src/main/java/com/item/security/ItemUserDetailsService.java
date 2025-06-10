package com.item.security;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.item.entity.User;
import com.item.repository.UserRepository;


/**
 * Custom implementation of Spring Security's UserDetailsService.
 * 
 * This service is responsible for loading user-specific data during authentication.
 * It retrieves user details from the database and maps user roles to Spring Security authorities.
 */

@Service
public class ItemUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public ItemUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	

	/**
     * Loads a user by username or email for authentication.
     * 
     * @param username The username or email of the user.
     * @return UserDetails object containing user credentials and authorities.
     * @throws UsernameNotFoundException if the user is not found.
     */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsernameOrEmail(username, username).orElseThrow(()->new UsernameNotFoundException("User not found"));
		
		List<GrantedAuthority> authorities= user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
