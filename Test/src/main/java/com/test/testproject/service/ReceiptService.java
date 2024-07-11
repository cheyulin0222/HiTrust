package com.test.testproject.service;

import org.springframework.stereotype.Service;

import com.test.testproject.enums.Location;
import com.test.testproject.req.ReceiptRequest;
import com.test.testproject.req.ShoppingItem;
import com.test.testproject.res.ReceiptResponse;
import com.test.testproject.res.ShoppingItemResponse;

import lombok.extern.slf4j.Slf4j;

import static com.test.testproject.enums.Location.CA;
import static com.test.testproject.enums.Location.NY;
import static com.test.testproject.enums.Type.FOOD;
import static com.test.testproject.enums.Type.CLOTHING;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReceiptService {
	
	private static final BigDecimal CA_RATE = new BigDecimal("0.0975");
    private static final BigDecimal NY_RATE = new BigDecimal("0.08875");

	public ReceiptResponse genReceipt(ReceiptRequest request) {
		var shoppiingItemList = buildShoppiingItemList(request.getShoppingList());
		var subtotal = getSubtotal(request.getShoppingList());
		var tax = getTax(request.getShoppingList());
		var total = subtotal.add(tax);
		return ReceiptResponse.builder()
				.shoppingList(shoppiingItemList)
				.subtotal(subtotal)
				.tax(tax)
				.total(total)
				.build();
	}
	
	private BigDecimal getSubtotal(List<ShoppingItem> list) {
		return list.stream()
			.map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal getTax(List<ShoppingItem> list) {
		return list.stream()
			.map(item -> roundUp(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).multiply(getRate(item))))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private BigDecimal getRate(ShoppingItem item) {
		log.info("name:{} ", item.getProductName() );
		if (isExempt(item)) {
			return BigDecimal.ZERO;
		}
		return getRateByLocation(item.getLocation());
	}
	
	private BigDecimal roundUp(BigDecimal value) {
		log.info("tax:{}", value);
		BigDecimal increment = new BigDecimal("0.05");
        BigDecimal divided = value.divide(increment, 0, RoundingMode.UP);
        return divided.multiply(increment);
	}
	
	private boolean isExempt(ShoppingItem item) {
		return (item.getType() == FOOD && item.getLocation() == CA) || (item.getType() == CLOTHING && item.getLocation() == NY);
	}
	
	private BigDecimal getRateByLocation(Location location) {
		log.info("location:{}", location.name());
		return location == CA ? CA_RATE : NY_RATE;
	}
	
	private List<ShoppingItemResponse> buildShoppiingItemList(List<ShoppingItem> list) {
		return	list.stream().map(item -> ShoppingItemResponse.builder()
					.productName(item.getProductName())
					.price(item.getPrice())
					.quantity(item.getQuantity())
					.build()
				).collect(Collectors.toList());
	}
	
	
	
	

}
