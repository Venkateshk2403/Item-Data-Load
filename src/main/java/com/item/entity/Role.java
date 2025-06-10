package com.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Entity class representing a user role in the system.
 * 
 * This class is mapped to the "roles" table in the database and is used
 * to define access levels (e.g., ADMIN, USER) for authorization purposes.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="roles")
public class Role {

	/**
     * Unique identifier for the role (auto-generated).
     */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
     * Name of the role (e.g., "ADMIN", "USER").
     */
	@Column(nullable = false)
	private String name;
}
