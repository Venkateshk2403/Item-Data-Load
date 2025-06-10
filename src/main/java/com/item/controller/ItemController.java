package com.item.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.item.dtos.ItemDto;
import com.item.entity.Item;
import com.item.service.ItemService;

import jakarta.validation.Valid;


/**
 * REST controller for managing item data.
 * 
 * Provides endpoints for creating, reading, updating, and deleting items.
 * Includes validation and role-based access control for sensitive operations.
 */

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private  ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves all items in the system.
     * 
     * @return List of ItemDto objects.
     */

	@GetMapping
	public List<ItemDto> getAllItems() {
		return itemService.getAll();
	}
	

	/**
     * Retrieves a specific item by its ID.
     * 
     * @param id ID of the item to retrieve.
     * @return ItemDto object wrapped in ResponseEntity with HTTP 200.
     */

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getById(@PathVariable int id) {
        ItemDto item = itemService.getById(id);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }
    

    /**
     * Adds a new item to the system.
     * 
     * Requires ADMIN role.
     * 
     * @param item ItemDto object with item details.
     * @return Created ItemDto with HTTP 201 status.
     */

    @PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ItemDto> addItem(@Valid @RequestBody ItemDto item){
		ItemDto addItem = itemService.addItem(item);
		return new ResponseEntity<>(addItem,HttpStatus.CREATED);
	}
    

    /**
     * Updates an existing item by its ID.
     * 
     * @param id ID of the item to update.
     * @param itemDto Updated item details.
     * @return Updated ItemDto with HTTP 201 status.
     */

    @PutMapping("/update/{id}")
    public ResponseEntity<ItemDto> updateById(@PathVariable int id, @RequestBody ItemDto item) {
        ItemDto updatedItem = itemService.upateItemById(id, item);
        return new ResponseEntity<>(updatedItem, HttpStatus.CREATED);
    }
    

    /**
     * Deletes an item by its ID.
     * 
     * Requires ADMIN role.
     * 
     * @param id ID of the item to delete.
     * @return HTTP 202 Accepted status.
     */

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        itemService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
