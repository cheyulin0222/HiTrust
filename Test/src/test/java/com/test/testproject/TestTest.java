package com.test.testproject;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.test.testproject.service.ReceiptService;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTest {

	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ReceiptService receiptService;
	
	@Test
	void first() throws Exception {
		
		String requestPayload = "{"
		        + "\"shoppingList\": ["
		        + "{ \"productName\": \"book\", \"price\": 17.99, \"quantity\": 1, \"location\": \"CA\", \"type\": \"OTHER\" },"
		        + "{ \"productName\": \"potato chips\", \"price\": 3.99, \"quantity\": 1, \"location\": \"CA\", \"type\": \"FOOD\" }"
		        + "]"
		        + "}";

    mockMvc.perform(post("/gen-receipt")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shoppingList[0].productName").value("book"))
        .andExpect(jsonPath("$.shoppingList[0].price", is(17.99)))
        .andExpect(jsonPath("$.shoppingList[0].quantity", is(1)))
        .andExpect(jsonPath("$.shoppingList[1].productName").value("potato chips"))
        .andExpect(jsonPath("$.shoppingList[1].price", is(3.99)))
        .andExpect(jsonPath("$.shoppingList[1].quantity", is(1)))
        .andExpect(jsonPath("$.subtotal", is(21.98)))
        .andExpect(jsonPath("$.tax", is(1.80)))
        .andExpect(jsonPath("$.total", is(23.78)));
	}
	
	@Test
	void second() throws Exception {
		
		String requestPayload = "{"
		        + "\"shoppingList\": ["
		        + "{ \"productName\": \"book\", \"price\": 17.99, \"quantity\": 1, \"location\": \"NY\", \"type\": \"OTHER\" },"
		        + "{ \"productName\": \"pencil\", \"price\": 2.99, \"quantity\": 3, \"location\": \"NY\", \"type\": \"FOOD\" }"
		        + "]"
		        + "}";

    mockMvc.perform(post("/gen-receipt")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shoppingList[0].productName").value("book"))
        .andExpect(jsonPath("$.shoppingList[0].price", is(17.99)))
        .andExpect(jsonPath("$.shoppingList[0].quantity", is(1)))
        .andExpect(jsonPath("$.shoppingList[1].productName").value("pencil"))
        .andExpect(jsonPath("$.shoppingList[1].price", is(2.99)))
        .andExpect(jsonPath("$.shoppingList[1].quantity", is(3)))
        .andExpect(jsonPath("$.subtotal", is(26.96)))
        .andExpect(jsonPath("$.tax", is(2.40)))
        .andExpect(jsonPath("$.total", is(29.36)));
	}
	
	@Test
	void third() throws Exception {
		
		String requestPayload = "{"
		        + "\"shoppingList\": ["
		        + "{ \"productName\": \"pencil\", \"price\": 2.99, \"quantity\": 2, \"location\": \"NY\", \"type\": \"OTHER\" },"
		        + "{ \"productName\": \"shirt\", \"price\": 29.99, \"quantity\": 1, \"location\": \"NY\", \"type\": \"CLOTHING\" }"
		        + "]"
		        + "}";

    mockMvc.perform(post("/gen-receipt")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shoppingList[0].productName").value("pencil"))
        .andExpect(jsonPath("$.shoppingList[0].price", is(2.99)))
        .andExpect(jsonPath("$.shoppingList[0].quantity", is(2)))
        .andExpect(jsonPath("$.shoppingList[1].productName").value("shirt"))
        .andExpect(jsonPath("$.shoppingList[1].price", is(29.99)))
        .andExpect(jsonPath("$.shoppingList[1].quantity", is(1)))
        .andExpect(jsonPath("$.subtotal", is(35.97)))
        .andExpect(jsonPath("$.tax", is(0.55)))
        .andExpect(jsonPath("$.total", is(36.52)));
	}

}
