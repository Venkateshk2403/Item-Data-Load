
package com.item.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.item.dtos.ItemDto;
import com.item.entity.Item;
import com.item.exceptions.ItemNotFoundException;
import com.item.exceptions.ValidationException;
import com.item.repository.ItemRepository;


/**
 * Unit tests for the ItemServiceImpl class.
 * 
 * This test class verifies the behavior of item-related service methods,
 * including validation logic and exception handling.
 */


@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ItemServiceImpl itemServiceImpl;

    private Item item1, item2, item3;
    private ItemDto itemDto1, itemDto2, itemDto3;
    private List<Item> items;
    
/**
 * Initializes test data before each test case.
 */

    @BeforeEach
    void init() {
        item1 = new Item(1, "Munch Chocolate Pack", "120.00", 100, true, 6, 30, "Switzerland", true, "Nestle",
                LocalDateTime.of(2023, 6, 15, 8, 30), LocalDate.of(2024, 12, 31));
        item2 = new Item(2, "Lifebuoy Soap", "75.00", 200, true, 7, 25, "India", true, "Unilever",
                LocalDateTime.of(2023, 7, 10, 10, 0), LocalDate.of(2025, 6, 30));
        item3 = new Item(3, "Lay’s Party Pack", "240.00", 150, false,null, 40, "USA", true, "PepsiCo",
                LocalDateTime.of(2023, 5, 20, 9, 45), LocalDate.of(2024, 11, 15));

        itemDto1 = new ItemDto(1, "Munch Chocolate Pack", "120.00", 100, true, 6, 30, "Switzerland", true, "Nestle",
                LocalDateTime.of(2023, 6, 15, 8, 30), LocalDate.of(2024, 12, 31));
        itemDto2 = new ItemDto(2, "Lifebuoy Soap", "75.00", 200, true, 7, 25, "India", true, "Unilever",
                LocalDateTime.of(2023, 7, 10, 10, 0), LocalDate.of(2025, 6, 30));
        itemDto3 = new ItemDto(3, "Lay’s Party Pack", "240.00", 150, false,null, 40, "USA", true, "PepsiCo",
                LocalDateTime.of(2023, 5, 20, 9, 45), LocalDate.of(2024, 11, 15));

        items = List.of(item1, item2, item3);
    }

/**
 	* Tests retrieval of all items from the service.
 * Verifies that the correct number of items is returned and mapped properly.
 */
    @Test
    void testGetAll() {
        when(itemRepository.findAll()).thenReturn(items);
        when(modelMapper.map(item1, ItemDto.class)).thenReturn(itemDto1);
        when(modelMapper.map(item2, ItemDto.class)).thenReturn(itemDto2);
        when(modelMapper.map(item3, ItemDto.class)).thenReturn(itemDto3);
        var result = itemServiceImpl.getAll();

        assertEquals(3, result.size());
        assertEquals("Munch Chocolate Pack", result.get(0).getItemName());
        
        verify(itemRepository,times(1)).findAll();
    }

/**
 	* Tests retrieval of a single item by its ID.
 * Ensures the correct item is returned and mapped.
 */
    @Test
    void testGetById() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item1));
        when(modelMapper.map(item1, ItemDto.class)).thenReturn(itemDto1);

        var result = itemServiceImpl.getById(1);

        assertNotNull(result);
        assertEquals("Munch Chocolate Pack", result.getItemName());
        
        verify(itemRepository,times(1)).findById(1);
    }
    @Test
    void testGetByIdNotFound() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemServiceImpl.getById(999));

        verify(itemRepository, times(1)).findById(999);
    }

/**
 	* Tests adding a new item.
 * Verifies that the item is saved and returned correctly.
 */
    @Test
    void testAddItem() {
        when(modelMapper.map(itemDto1, Item.class)).thenReturn(item1);
        when(itemRepository.save(item1)).thenReturn(item1);
        when(modelMapper.map(item1, ItemDto.class)).thenReturn(itemDto1);

        var result = itemServiceImpl.addItem(itemDto1);

        assertNotNull(result);
        assertEquals("Munch Chocolate Pack", result.getItemName());
        
        verify(itemRepository,times(1)).save(item1);
    }

/**
 	* Tests updating an existing item by ID.
 * Ensures the item is updated and mapped correctly.
 */
    @Test
    void testUpdateItemById() {
    	when(itemRepository.existsById(anyInt())).thenReturn(true);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item1));
        when(itemRepository.save(item1)).thenReturn(item1);
        when(modelMapper.map(item1, ItemDto.class)).thenReturn(itemDto2);

        var result = itemServiceImpl.upateItemById(1, itemDto2);

        assertNotNull(result);
        assertEquals("Lifebuoy Soap", result.getItemName());
        

		verify(itemRepository,times(1)).existsById(1);
		
		verify(itemRepository,times(1)).findById(1);
		
		verify(itemRepository,times(1)).save(item1);
    }

/**
 	* Tests behavior when updating a non-existent item.
 * Expects an ItemNotFoundException to be thrown.
 */
	@Test
	void testUpdateWhenItemNotPresent() {
		when(itemRepository.existsById(anyInt())).thenReturn(false);
		
		assertThrows(ItemNotFoundException.class,()->itemServiceImpl.upateItemById(1, itemDto1));
		
		verify(itemRepository,times(1)).existsById(1);
		
		verify(itemRepository,never()).findById(1);
		
		verify(itemRepository,never()).save(item1);
	}

/**
 	* Tests deletion of an item by ID.
 * Verifies that the delete operation is called.
 */
    @Test
    void testDeleteById() {
        when(itemRepository.existsById(anyInt())).thenReturn(true);

        itemServiceImpl.deleteById(1);

        verify(itemRepository, times(1)).deleteById(1);
    }

/**
 	* Tests validation failure when itemPack is false but itemContents is present.
 * Expects a ValidationException to be thrown.
 */
    @Test
    void testDeleteWhenItemNotFound() {
        when(itemRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> itemServiceImpl.deleteById(1));
        
        verify(itemRepository,times(1)).existsById(1);
		
		verify(itemRepository,never()).deleteById(1);
    }

   /**
    * Tests validation failure when itemPack is false but itemContents is present.
 * Expects a ValidationException to be thrown.
 */
    @Test
    void testAddItemWhenItemPackIsFalseAndContentPresent() {
        item3.setItemPack(false);
        item3.setItemContents(10); // invalid case
        when(modelMapper.map(itemDto3, Item.class)).thenReturn(item3);

        assertThrows(ValidationException.class, () -> itemServiceImpl.addItem(itemDto3));

        verify(itemRepository, never()).save(any(Item.class));
    }
    
/**
 	* Tests validation failure when itemPack is true but itemContents is null.
 * Expects a ValidationException to be thrown.
 */

    @Test
    void testAddItemWhenItemPackIsTrueButContentsNull() {
        item1.setItemContents(null); // invalid case
        when(modelMapper.map(itemDto1, Item.class)).thenReturn(item1);

        assertThrows(ValidationException.class, () -> itemServiceImpl.addItem(itemDto1));

        verify(itemRepository, never()).save(any(Item.class));
    }


/**
 	* Tests valid case where itemPack is false and itemContents is null.
 * Verifies that the item is saved successfully.
 */
    @Test
    void testAddItemWhenItemPackIsFalseAndContentsNull() {
      
        when(modelMapper.map(itemDto3, Item.class)).thenReturn(item3);
        when(itemRepository.save(item3)).thenReturn(item3);
        when(modelMapper.map(item3, ItemDto.class)).thenReturn(itemDto3);

        var result = itemServiceImpl.addItem(itemDto3);

        assertNotNull(result);
        assertEquals("Lay’s Party Pack", result.getItemName());

        verify(itemRepository, times(1)).save(item3);
    }

/**
 	* Tests valid case where itemPack is true and itemContents is present.
 * Verifies that the item is saved successfully.
 */
    @Test
    void testAddItemWhenItemPackIsTrueAndContentsPresent() {
        when(modelMapper.map(itemDto1, Item.class)).thenReturn(item1);
        when(itemRepository.save(item1)).thenReturn(item1);
        when(modelMapper.map(item1, ItemDto.class)).thenReturn(itemDto1);

        var result = itemServiceImpl.addItem(itemDto1);

        assertNotNull(result);
        assertEquals("Munch Chocolate Pack", result.getItemName());

        verify(itemRepository, times(1)).save(item1);
    }

}
