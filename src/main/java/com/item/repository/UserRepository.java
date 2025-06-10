package com.item.repository;

/**
 * Repository interface for User entity.
 * 
 * Extends JpaRepository to provide CRUD operations and custom query methods
 * for interacting with the "users" table in the database.
 */

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.item.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	boolean existsByUsernameOrEmail(String username,String email);
	
}
