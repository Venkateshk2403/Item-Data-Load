package com.item.service;

import java.util.List;

import com.item.dtos.ItemDto;

/**
 * Service interface for managing item data.
 * 
 * Defines the core business operations for creating, retrieving, updating,
 * and deleting items in the system.
 */

public interface ItemService {
	
    List<ItemDto> getAll();
    
    ItemDto getById(int id);
    
    ItemDto addItem(ItemDto itemDto);
    
    ItemDto upateItemById(int id, ItemDto itemDto);
    
    void deleteById(int id);
    
}

