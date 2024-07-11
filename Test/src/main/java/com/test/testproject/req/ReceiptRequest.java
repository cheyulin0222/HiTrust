package com.test.testproject.req;

import java.util.List;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequest {

	@Valid
	private List<ShoppingItem> shoppingList;
}
