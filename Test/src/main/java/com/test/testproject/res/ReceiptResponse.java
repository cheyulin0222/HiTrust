package com.test.testproject.res;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {

	private List<ShoppingItemResponse> shoppingList;
	private BigDecimal subtotal;
	private BigDecimal tax;
	private BigDecimal total;
}
