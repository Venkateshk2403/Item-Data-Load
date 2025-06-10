package com.item.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.item.entity.Item;

/**
 * Repository interface for Item entity.
 * 
 * Extends JpaRepository to provide CRUD operations and query methods
 * for interacting with the "items" table in the database.
 * 
 * JpaRepository provides:
 * - save(), findById(), findAll(), deleteById(), etc.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
	
}
