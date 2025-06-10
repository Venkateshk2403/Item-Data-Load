package com.item.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for item data.
 * 
 * This class is used to encapsulate item information for API requests and responses.
 * It includes validation annotations to ensure data integrity before processing.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;
	
	@NotBlank(message ="Item name is required")
	@Pattern(regexp = "^[A-Za-z ]{3,}$", message = "Item name must be at least 3 characters long and contain only alphabets and spaces")
	private String itemName;
	
	@NotBlank(message ="Item cost is required")
	@Pattern(regexp = "\\d+(\\.\\d{1,2})?$",message = "Item cost must be a valid number with up to two decimal places only")
	private String itemCost;
	
	@NotNull(message ="Item quantity should be atleast 1 or greater than 1 ")
	private int itemQuantity;
	
	@NotNull(message = "Item pack should be True or False")
	private Boolean itemPack;
	
	private Integer itemContents;
	
	@NotNull(message ="Item dimensions must be 1 or greater than 1")
	private int itemDimensions;

	@NotBlank(message ="Item origin location is required")
	@Pattern(regexp = "^[A-Za-z ]{3,}$", message = "Item Origin Location must be at least 3 characters long and contain only alphabets and spaces")
	private String itemOriginLocation;

	@NotNull(message ="Item ship cannot be Null,it must be true/false")
	private Boolean itemShip;
	
	@NotBlank(message = "Item company is required")
	@Pattern(regexp = "^[A-Za-z ]{3,}$", message = "Item Company name must be at least 3 characters long and contain only alphabets and spaces")
	private String itemCompany;
	
	@NotNull(message = "Item manufacturing date and time should not be null")
	@PastOrPresent(message = "Manufacturing date should be of past/present")
	private LocalDateTime itemManufacturingDateTime;
	
	@NotNull(message = "Item expiry date is required")
	@FutureOrPresent(message = "Item expiry date must be in the future/present")
	private LocalDate itemExpiryDate;
}
