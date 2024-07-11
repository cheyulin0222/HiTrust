package com.test.testproject.req;

import java.math.BigDecimal;

import com.test.testproject.enums.Location;
import com.test.testproject.enums.Type;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShoppingItem {

	@NotBlank
	@Schema(description = "Product Name")
	private String productName;
	
	@NotNull
	@Schema(description = "Price")
	private BigDecimal price;
	
	@NotNull
	@Schema(description = "Quantity")
	private int quantity;
	
	@NotNull
	@Schema(description = "Location")
	private Location location;
	
	@NotNull
	@Schema(description = "Type")
	private Type type;
}
