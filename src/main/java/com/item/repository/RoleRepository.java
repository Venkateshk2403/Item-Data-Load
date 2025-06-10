package com.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.item.entity.Role;

/**
 * Repository interface for Role entity.
 * 
 * Extends JpaRepository to provide CRUD operations and custom query methods
 * for interacting with the "roles" table in the database.
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(String name);
	
	boolean existsByName(String name );
}
