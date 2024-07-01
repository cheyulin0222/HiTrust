package com.hitrsutpay.sa.backend;

import static com.hitrsutpay.sa.backend.constant.TestConstant.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitrsutpay.sa.backend.security.JwtUtils;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
class TpSRIDataTests {
	
	String jwtToken = JwtUtils.generateToken(
			User.builder()
			.username	("jackyhsieh@hitrust.com")
			.password	("")
			.roles		("admin")
			.build());
	
	
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	
	final String testUri = "https://code.jquery.com/jquery-1.0.js";
	final String testSRi = "sha256-gmxkwTy2NtwVlrGfePEfyK79h5LwCqgVGrtLIlKOc9c=";
	final String testUri2 = "https://code.jquery.com/jquery-1.0.1.js";
	final String testSRi2 = "sha256-7FEp9UKEmqd37mBUMmo5uStN/zA2wgVf+/XwJJplFeM=";
	
	// V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V--- SA1102 子資源完整性維護-新增 ---V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V //
	
	@Test 
	@DisplayName("001新增子資源完整性")
	void testCreateTpSRIData() throws Exception {	
		final String testUri = "https://code.jquery.com/jquery-1.0.js";
		final String testSRi = "sha256-gmxkwTy2NtwVlrGfePEfyK79h5LwCqgVGrtLIlKOc9c=";
		final String testUri2 = "https://code.jquery.com/jquery-1.0.1.js";
		
		// 測試驗算 Request
		String requestJson = "{"
				+ "  \"uri\": \""+testUri+"\","
				+ "  \"sri\": \""+testSRi+"\""
				+ "}";
		
		
		// 測試驗算
		mockMvc.perform(MockMvcRequestBuilders.post("/sri/create-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(CREATE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
		
		// 測試即時計算 Request
		String requestJson2 = "{"
				+ "  \"uri\": \""+testUri2+"\","
				+ "  \"sri\": \""+""+"\""
				+ "}";
		
		// 測試即時計算
		mockMvc.perform(MockMvcRequestBuilders.post("/sri/create-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson2)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(CREATE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
		
	}

	
	@Test 
	@DisplayName("002修改子資源完整性")
	void testUpdateTpSRIData() throws Exception {	
		
		// 測試驗算 Request
		String requestJson = "{"
				+ "  \"previousData\": \"{}\","
				+ "  \"uri\": \""+testUri+"\","
				+ "  \"sri\": \""+""+"\""
				+ "}";
		
		
		// 測試驗算
		mockMvc.perform(MockMvcRequestBuilders.post("/sri/update-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
		
		// 測試即時計算 Request
		String requestJson2 = "{"
				+ "  \"previousData\": \"{}\","
				+ "  \"uri\": \""+testUri2+"\","
				+ "  \"sri\": \""+testSRi2+"\""
				+ "}";
		
		// 測試即時計算
		mockMvc.perform(MockMvcRequestBuilders.post("/sri/update-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson2)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
		
	}
	
	
	@Test 
	@DisplayName("003查詢子資源完整性")
	void testGetAllTpSRIData() throws Exception {	
		final String testUriPart = "";
		final String testUriPart2 = "ajax";
		
		// 測試全部搜尋 Request
		String requestJson = "{"
				+ "  \"uri\": \""+testUriPart+"\""
				+ "}";
		
		
		// 測試全部搜尋
		mockMvc.perform(MockMvcRequestBuilders.get("/sri/get-all-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.param("uri", testUriPart)
				//.content(requestJson)																			// Set the request body as a UTF-8 String
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		;
		
		// 測試模糊搜尋 Request
		String requestJson2 = "{"
				+ "  \"uri\": \""+testUriPart2+"\""
				+ "}";
		
		// 測試即時計算
		mockMvc.perform(MockMvcRequestBuilders.get("/sri/get-all-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.param("uri", testUriPart)
				//.content(requestJson2)																		// Set the request body as a UTF-8 String
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		;
		
		// 測試全部搜尋+分頁 Request
		String requestJson3 = "{"
				+ "  \"uri\": \""+testUriPart+"\""
				+ "}";
		
		// 測試即時計算
		mockMvc.perform(MockMvcRequestBuilders.get("/sri/get-all-sri-data?page=2&size=1")						// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.param("uri", testUriPart)
				//.content(requestJson3)																		// Set the request body as a UTF-8 String
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		;
		
	}
	
	@Test 
	@DisplayName("004刪除子資源完整性")
	void testDeleteTpSRIData() throws Exception {	
		
		// 測試全部搜尋 Request
		String requestJson = "{"
				+ "  \"previousData\": \"{}\","
				+ "  \"uri\": \""+testUri+"\""
				+ "}";
		
		
		// 測試全部搜尋
		mockMvc.perform(MockMvcRequestBuilders.delete("/sri/delete-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(DELETE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
		
		// 測試模糊搜尋 Request
		String requestJson2 = "{"
				+ "  \"previousData\": \"{}\","
				+ "  \"uri\": \""+testUri2+"\""
				+ "}";
		
		// 測試即時計算
		mockMvc.perform(MockMvcRequestBuilders.delete("/sri/delete-sri-data")										// Set API URI
				.header("Authorization", "Bearer " + jwtToken)													// Set authorization header
				.content(requestJson2)																			// Set the request body as a UTF-8 String
				.contentType(MediaType.APPLICATION_JSON)														// Set the Content-Type header of the request
				.accept(MediaType.APPLICATION_JSON)																// Set the Accept header to the given media types
				)
		.andExpect(MockMvcResultMatchers.status().isOk())														// Expect situation(HTTTP STATUS)
		.andExpect(MockMvcResultMatchers.content().json(DELETE_SUCCESSFUL_RESPONSE_JSON))						// Expect situation [Plan A] Json
		;
	
	}
	
	
	
}
