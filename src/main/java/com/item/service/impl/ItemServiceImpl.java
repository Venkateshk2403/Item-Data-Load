package com.item.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.item.dtos.ItemDto;
import com.item.entity.Item;
import com.item.exceptions.ItemNotFoundException;
import com.item.exceptions.ValidationException;
import com.item.repository.ItemRepository;
import com.item.service.ItemService;
	

/**
 * Service implementation for managing items.
 * 
 * Provides methods to perform CRUD operations on items,
 * including validation logic for item packs.
 */

@Service
public class ItemServiceImpl implements ItemService {

	private ItemRepository itemRepository;
	private ModelMapper modelMapper;

	public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper) {
		this.itemRepository = itemRepository;
		this.modelMapper = modelMapper;
	}

	/**
     * Retrieves all items from the database.
     *
     * @return a list of item DTOs
     */

	@Override
	public List<ItemDto> getAll() {
		return itemRepository.findAll().stream().map(item -> modelMapper.map(item, ItemDto.class)).toList();
	}

	/**
     * Retrieves an item by its ID.
     *
     * @param id the ID of the item
     * @return the item DTO
     * @throws ItemNotFoundException if the item is not found
     */

	@Override
	public ItemDto getById(int id) {
		Item item = itemRepository.findById(id)
				.orElseThrow(() -> new ItemNotFoundException("Item with the Id " + id + " not found."));
		return modelMapper.map(item, ItemDto.class);
	}

	/**
     * Adds a new item to the database after validation.
     *
     * @param itemDto the item data to be added
     * @return the saved item DTO
     * @throws ValidationException if the item fails validation
     */

	@Override
	public ItemDto addItem(ItemDto itemDto) {
		Item item = modelMapper.map(itemDto, Item.class);
		validateItem(item);
		Item saved = itemRepository.save(item);
		return modelMapper.map(saved, ItemDto.class);
	}

	/**
     * Updates an existing item by its ID.
     *
     * @param id the ID of the item to update
     * @param itemDto the updated item data
     * @return the updated item DTO
     * @throws ItemNotFoundException if the item does not exist
     * @throws ValidationException if the updated item fails validation
     */

//	@Override
//	public ItemDto upateItemById(int id, ItemDto itemDto) {
//		if (!itemRepository.existsById(id)) {
//			throw new ItemNotFoundException("Item with id " + id + " not found");
//		}
//
//		Item existingItem = itemRepository.findById(id)
//				.orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));
//		
//		modelMapper.map(itemDto, existingItem); 
//	//	validateItem(existingItem);
//		
//		Item updated = itemRepository.save(existingItem);
//		return modelMapper.map(updated, ItemDto.class);
//	}
	@Override
	public ItemDto upateItemById(int id, ItemDto itemDto) {
	    if (!itemRepository.existsById(id)) {
	        throw new ItemNotFoundException("Item with id " + id + " not found");
	    }

	    Item existingItem = itemRepository.findById(id)
	            .orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));

	    
	    if (itemDto.getItemName() != null) {
	        existingItem.setItemName(itemDto.getItemName());
	    }

	    if (itemDto.getItemCost() != null) {
	        existingItem.setItemCost(itemDto.getItemCost());
	    }

	    if (itemDto.getItemQuantity() != 0 && itemDto.getItemQuantity() > 0) {
	        existingItem.setItemQuantity(itemDto.getItemQuantity());
	    }

	    if (itemDto.getItemPack() != null) {
	        boolean newItemPack = itemDto.getItemPack();
	      
	        if (newItemPack && itemDto.getItemContents() == null) {
	            throw new ValidationException("Item contents must be provided when Item Pack is true");
	        }

	        if (!newItemPack && itemDto.getItemContents() != null) {
	            throw new ValidationException("Item contents cannot be populated when Item Pack is false");
	        }
	        existingItem.setItemPack(newItemPack);
	        existingItem.setItemContents(null);
	    }

	    if (itemDto.getItemContents() != null) {
	        existingItem.setItemContents(itemDto.getItemContents());
	    }

	    if (itemDto.getItemDimensions() != 0 && itemDto.getItemDimensions() > 0) {
	        existingItem.setItemDimensions(itemDto.getItemDimensions());
	    }

	    if (itemDto.getItemOriginLocation() != null) {
	        existingItem.setItemOriginLocation(itemDto.getItemOriginLocation());
	    }

	    if (itemDto.getItemShip() != null) {
	        existingItem.setItemShip(itemDto.getItemShip());
	    }

	    if (itemDto.getItemCompany() != null) {
	        existingItem.setItemCompany(itemDto.getItemCompany());
	    }

	    if (itemDto.getItemManufacturingDateTime() != null) {
	        existingItem.setItemManufacturingDateTime(itemDto.getItemManufacturingDateTime());
	    }

	    if (itemDto.getItemExpiryDate() != null) {
	        existingItem.setItemExpiryDate(itemDto.getItemExpiryDate());
	    }

	    Item updated = itemRepository.save(existingItem);
	    return modelMapper.map(updated, ItemDto.class);
	}


	/**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to delete
     * @throws ItemNotFoundException if the item does not exist
     */

	@Override
	public void deleteById(int id) {
		if (!itemRepository.existsById(id)) {
			throw new ItemNotFoundException("Item with id " + id + " not found");
		}
		itemRepository.deleteById(id);
	}

	/**
     * Validates the item based on whether it is a pack or not.
     *
     * @param item the item to validate
     * @throws ValidationException if the item pack rules are violated
     */

	public void validateItem(Item item) {

		if (item.isItemPack()) { 
			if (item.getItemContents() == null) {
				throw new ValidationException("Item contents must be populated when Item Pack is true");
			}
		} else { 
			if (item.getItemContents() != null) {
				throw new ValidationException("Item contents cannot be populated when Item Pack is false");
			}
		}
	}


}
