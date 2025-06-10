package com.item.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.item.dtos.ItemDto;
import com.item.exceptions.ItemNotFoundException;
import com.item.service.ItemService;

/**
 * Integration tests for the ItemController using MockMvc.
 * 
 * This test class verifies the behavior of the item-related endpoints,
 * including retrieval, creation, update, and deletion of items.
 * 
 * ItemService to isolate controller logic.
 */

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper mapper;

    ItemDto item1;
    ItemDto item2;
    ItemDto item3;
    List<ItemDto> items;
    

    /**
     * Initializes test data before each test case.
     */

    @BeforeEach
    void init() {
        item1 = new ItemDto(1, "Munch Chocolate Pack", "120.00", 100, true, 6, 30, "Switzerland", true, "Nestle",
                LocalDateTime.of(2023, 6, 15, 8, 30), LocalDate.of(2025, 12, 31));
        item2 = new ItemDto(2, "Lifebuoy Soap", "75.00", 200, true, 7, 25, "India", true, "Unilever",
                LocalDateTime.of(2023, 7, 10, 10, 0), LocalDate.of(2025, 6, 30));
        item3 = new ItemDto(3, "Lay’s Party Pack", "240.00", 150, true, 12, 40, "USA", true, "PepsiCo",
                LocalDateTime.of(2023, 5, 20, 9, 45), LocalDate.of(2025, 11, 15));

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);
    }

    /**
     * Tests retrieval of all items.
     */

    @Test
    @WithMockUser
    void testGetAllItems() throws Exception {
        when(itemService.getAll()).thenReturn(items);

        mockmvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(3)))
                .andExpect(jsonPath("$[0].itemName", is("Munch Chocolate Pack")));
    }

    /**
     * Tests retrieval of a single item by ID.
     */
    
    @Test
    @WithMockUser
    void testGetById() throws Exception {
        when(itemService.getById(anyInt())).thenReturn(item2);

        mockmvc.perform(get("/api/items/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemOriginLocation", is("India")));
    }

    /**
     * Tests adding a new item.
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddItem() throws Exception {
        when(itemService.addItem(any(ItemDto.class))).thenReturn(item1);

        var jsonItem = mapper.writeValueAsString(item1);

        mockmvc.perform(post("/api/items/add")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemName", is("Munch Chocolate Pack")));
    }
    

    /**
     * Tests updating an existing item by ID.
     */
    
    @Test
    @WithMockUser
    void testUpdateById() throws Exception {
        when(itemService.upateItemById(anyInt(), any(ItemDto.class))).thenReturn(item1);

        var jsonItem = mapper.writeValueAsString(item1);

        mockmvc.perform(put("/api/items/update/1")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.itemName", is("Munch Chocolate Pack")));
    }

    /**
     * Tests deletion of an item by ID.
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteById() throws Exception {
        doNothing().when(itemService).deleteById(anyInt());

        mockmvc.perform(delete("/api/items/delete/3"))
                .andExpect(status().isAccepted());
    }

/**
 	* Tests retrieval of a single item by ID when the item does not exist.
 * 
 * This test simulates a scenario where the service throws a ResourceNotFoundException,
 * and verifies that the controller responds with a 404 Not Found status and appropriate message.
 */

    @Test
    @WithMockUser
    void testGetById_NotFound() throws Exception {
        when(itemService.getById(anyInt())).thenThrow(new ItemNotFoundException("Item not found"));

        mockmvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Item not found")));
    }
    
/**
 * Tests updating an item by ID when the item does not exist.
 * 
 * This test ensures that the controller returns a 404 Not Found status
 * when the service layer throws a ResourceNotFoundException.
 */

    @Test
    @WithMockUser
    void testUpdateById_NotFound() throws Exception {
        when(itemService.upateItemById(anyInt(), any(ItemDto.class)))
                .thenThrow(new ItemNotFoundException("Item not found"));

        var jsonItem = mapper.writeValueAsString(item1);

        mockmvc.perform(put("/api/items/update/999")
                        .content(jsonItem)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Item not found")));
    }

/**
 	* Tests deletion of an item by ID when the item does not exist.
 * 
 * This test verifies that the controller handles the ResourceNotFoundException
 * thrown by the service and returns a 404 Not Found status.
 */

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteById_NotFound() throws Exception {
        doNothing().when(itemService).deleteById(anyInt());
        // Simulate exception
        Mockito.doThrow(new ItemNotFoundException("Item not found"))
                .when(itemService).deleteById(999);

        mockmvc.perform(delete("/api/items/delete/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Item not found")));
    }






}
