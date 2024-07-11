package com.test.testproject.res;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingItemResponse {

	private String productName;
	private BigDecimal price;
	private int quantity;
}
