package com.hitrsutpay.sa.backend;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

import org.hamcrest.Matchers;
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

import com.hitrsutpay.sa.backend.constant.TestConstant;
import com.hitrsutpay.sa.backend.controller.TokenPayMappingController;
import com.hitrsutpay.sa.backend.enums.ErrorType;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.security.JwtUtils;
import com.hitrsutpay.sa.backend.security.constant.JwtConstant;
import com.hitrsutpay.sa.backend.service.TokenPayMappingService;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TokenPayMappingTests {

	String jwtToken = JwtUtils.generateToken(
			User.builder()
			.username("jeremylin@hitrust.com")
			.password	("pizza28825252")
			.roles		("admin")
			.build()
	);
	
	final String TOKEN = JwtConstant.TOKEN_PREFIX + " " + jwtToken;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TokenPayMappingService tokenPayMappingService;
	
	@Test
	@DisplayName("001查詢速速付對應-檢核查詢資料及確認將要新增修改資料是否存在")
	void testGetTokenPayMapping() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authMerId").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authStatus").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authMerId").value(not(hasItem("D0125"))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authMerId").value(not(hasItem("D0124"))));
	}
	
	@Test
	@DisplayName("002儲存速速付對應-新增")
	void testSaveTokenPayMappingCreate() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = TestConstant.SAVE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	

	@Test
	@DisplayName("003查詢速速復對應-確認新增資料存在")
	void testGetTokenPayMappingCreate() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0125' && @.authStatus=='AUTH')]").exists());	
	}
	

	@Test
	@DisplayName("004儲存速速付對應-修改")
	void testSaveTokenPayMappingUpdate() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[{\\\"authMerId\\\":\\\"D0125\\\",\\\"authStatus\\\":\\\"AUTH\\\"}]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"CANCEL\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = TestConstant.SAVE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("005查詢速速付對應-確認資料已修改")
	void testGetTokenPayMappingUpdate() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0125' && @.authStatus=='CANCEL')]").exists());	
	}
	
	@Test
	@DisplayName("006儲存速速付對應-新增修改")
	void testSaveTokenPayMappingUpdateAndCreate() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[{\\\"authMerId\\\":\\\"D0125\\\",\\\"authStatus\\\":\\\"CANCEL\\\"}]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    },"
			+ "    {"
			+ "      \"authMerId\": \"D0124\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = TestConstant.SAVE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("007查詢速速付對應-確認已新增修改")
	void testGetTokenPayMappingUpdateAndCreate() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0125' && @.authStatus=='AUTH')]").exists())	
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0124' && @.authStatus=='AUTH')]").exists());	
	}
	
	@Test
	@DisplayName("008儲存速速付對應-多筆修改")
	void testSaveTokenPayMappingMultipleUpdate() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[{\\\"authMerId\\\":\\\"D0125\\\",\\\"authStatus\\\":\\\"AUTH\\\"},{\\\"authMerId\\\":\\\"D0124\\\",\\\"authStatus\\\":\\\"AUTH\\\"}]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"CANCEL\""
			+ "    },"
			+ "    {"
			+ "      \"authMerId\": \"D0124\","
			+ "      \"authStatus\": \"CANCEL\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = TestConstant.SAVE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("009查詢速速付對應-確認多筆修改")
	void testGetTokenPayMappingMultipleUpdate() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0125' && @.authStatus=='CANCEL')]").exists())	
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.authMerId=='D0124' && @.authStatus=='CANCEL')]").exists());	
	}
	
	@Test
	@DisplayName("010查詢速速付對應-storeid長度檢核")
	void testGetTokenPayMappingOverTen() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "01234567890");
		
		String responseJson = 
			"{"
			+ "  \"data\": \"ConstraintViolationException: getTokenPayMapping.storeId: size must be between 1 and 10\","
			+ "  \"code\": \"SYSTEM\","
			+ "  \"message\": \"系統錯誤\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("011查詢速速付對應-storeid特殊符號檢核")
	void testGetTokenPayMappingSpecialChracter() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "!");
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: MerchantErrorCode_002  商代只能輸入英數字\","
			+ "  \"code\": \"BUSINESS[MerchantErrorCode_002]\","
			+ "  \"message\": \"商代只能輸入英數字\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("012查詢速速付對應-不存在的storeid")
	void testGetTokenPayMappingNoMerid() throws Exception {
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "00000");
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: MerchantErrorCode_001  查無此商代\","
			+ "  \"code\": \"BUSINESS[MerchantErrorCode_001]\","
			+ "  \"message\": \"查無此商代\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("013查詢速速付對應-刪除測試資料檢核")
	void testGetTokenPayMappingDeleteCheck() throws Exception {
		
		tokenPayMappingService.deleteTokenPayMappingById("D0148", "D0125");
		tokenPayMappingService.deleteTokenPayMappingById("D0148", "D0124");
		
		String uri = TokenPayMappingController.TOKENPAY_MAPPING_GET_URL;
		uri = uri.replace("{storeId}", "D0148");
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authMerId").value(not(hasItem("D0125"))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].authMerId").value(not(hasItem("D0124"))));
	}
	
	@Test
	@DisplayName("014儲存速速付對應-不存在的regMerid檢核")
	void testSaveTokenPayMappingNoRegMerid() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"00000\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: MerchantErrorCode_001  查無此商代\","
			+ "  \"code\": \"BUSINESS[MerchantErrorCode_001]\","
			+ "  \"message\": \"查無此商代\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("015儲存速速付對應-不存在的authMerid")
	void testSaveTokenPayMappingNoAuthMerid() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"00000\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: MerchantErrorCode_001  查無此商代\","
			+ "  \"code\": \"BUSINESS[MerchantErrorCode_001]\","
			+ "  \"message\": \"查無此商代\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("016儲存速速付對應-regMerid和authMerid相同")
	void testSaveTokenPayMappingRegMeridEqualsAuthMerid() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0148\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: TokenPayMappingErrorCode_001  儲存失敗，授權商代和被授權商代重複\","
			+ "  \"code\": \"BUSINESS[TokenPayMappingErrorCode_001]\","
			+ "  \"message\": \"儲存失敗，授權商代和被授權商代重複\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("017儲存速速付對應-string參數為null")
	void testSaveTokenPayMappingNull() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": null,"
			+ "  \"regMerId\": null,"
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": null,"
			+ "      \"authStatus\": null"
			+ "    }"
			+ "  ]"
			+ "}";
		
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("authStatus:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("regMerId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("authMerId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")));
	}
	
	@Test
	@DisplayName("018儲存速速付對應-String參數為空格")
	void testSaveTokenPayMappingBlank() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \" \","
			+ "  \"regMerId\": \" \","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \" \","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("regMerId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("authMerId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")));
	}
	
	@Test
	@DisplayName("019儲存速速付對應-storeid特殊符號檢核")
	void testSaveTokenPayMappingSpecialCharacter() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"!\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"!\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("regMerId:Must contain only alphanumeric characters")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("authMerId:Must contain only alphanumeric characters")));
	}
	
	@Test
	@DisplayName("020儲存速速付對應-storeid長度檢核")
	void testSaveTokenPayMappingOverTen() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"01234567890\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"01234567890\","
			+ "      \"authStatus\": \"AUTH\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("regMerId:size must be between 1 and 10")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("authMerId:size must be between 1 and 10")));
	}
	
	@Test
	@DisplayName("021儲存速速付對應-saveList為null")
	void testSaveTokenPayMappingSaveListNull() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": null"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("saveList:must not be empty")));
	}
	
	@Test
	@DisplayName("022儲存速速付對應-saveList為null")
	void testSaveTokenPayMappingSaveListBlank() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": []"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("saveList:must not be empty")));
	}
	
	@Test
	@DisplayName("023儲存速速付對應-authStatus帶其他值")
	void testSaveTokenPayMappingEnum() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"previousData\": \"[]\","
			+ "  \"regMerId\": \"D0148\","
			+ "  \"saveList\": ["
			+ "    {"
			+ "      \"authMerId\": \"D0125\","
			+ "      \"authStatus\": \"TEST\""
			+ "    }"
			+ "  ]"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TokenPayMappingController.TOKENPAY_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("InvalidFormatException: Cannot deserialize value of type `com.hitrsutpay.sa.backend.enums.TokenPayStatus` from String \"TEST\": not one of the values accepted for Enum class: [AUTH, CANCEL]")));
	}
	
	
}
