package com.hitrsutpay.sa.backend;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.instanceOf;
import static com.hitrsutpay.sa.backend.utils.MatcherUtil.isValidTimeFormat;
import static com.hitrsutpay.sa.backend.constant.TestConstant.CREATE_SUCCESSFUL_RESPONSE_JSON;
import static com.hitrsutpay.sa.backend.constant.TestConstant.UPDATE_SUCCESSFUL_RESPONSE_JSON;
import static com.hitrsutpay.sa.backend.enums.TempletPageType.EZP;
import static com.hitrsutpay.sa.backend.enums.TempletPageType.PAYPAGE;
import static com.hitrsutpay.sa.backend.controller.TpTempletPageController.TEMPLET_PAGE_GET_URL;
import static com.hitrsutpay.sa.backend.controller.TpTempletPageController.TEMPLET_PAGE_UPDATE_URL;
import static com.hitrsutpay.sa.backend.controller.TpTempletPageController.TEMPLET_PAGE_CREATE_URL;


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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitrsutpay.sa.backend.domain.TpTempletPagePK;
import com.hitrsutpay.sa.backend.enums.ErrorType;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.security.JwtUtils;
import com.hitrsutpay.sa.backend.security.constant.JwtConstant;
import com.hitrsutpay.sa.backend.service.TpTempletPageService;
import com.hitrsutpay.sa.backend.utils.StringTool;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TempletPageTests {

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
	private ObjectMapper objectMapper;
	@Autowired
	private TpTempletPageService templetPageService;
	
	private static final TpTempletPagePK PAYPAGE_TEST_ID = new TpTempletPagePK("TEST", "JY001", PAYPAGE);
	private static final TpTempletPagePK EZP_TEST_ID = new TpTempletPagePK("", "JY001", EZP);
	private static final String PAYPAGE_URL = TEMPLET_PAGE_GET_URL.replace("{type}", PAYPAGE.name());
	private static final String EZP_URL = TEMPLET_PAGE_GET_URL.replace("{type}", EZP.name());

	
	@Test
	@DisplayName("001查詢付款頁面樣式-付款頁")
	void testGetAllPayPageTempletPage() throws Exception {
		
		templetPageService.deleteTempletPage(PAYPAGE_TEST_ID);
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(PAYPAGE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.size").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].country").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].tempetId").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].updateTime").value(everyItem(isValidTimeFormat())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].status").value(everyItem(anyOf(equalTo("NO"), equalTo("YES")))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].tempetId").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.number").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.sorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.unsorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.first").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.sorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.unsorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.paged").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.unpaged").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.last").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='TEST' && @.templetId=='JY001')]").doesNotExist());
	}
	
	@Test
	@DisplayName("002新增付款頁面樣式-付款頁")
	void testCreatePayPageTempletPage() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(CREATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(PAYPAGE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='TEST' && @.templetId=='JY001' && @.status=='YES')]").exists());
	}
	
	@Test
	@DisplayName("003新增付款頁面樣式-重複的ID")
	void testCreatePayPageTempletPageIdExist() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"id\": {"
			+ "      \"country\": \"TEST\","
			+ "      \"templetId\": \"JY001\","
			+ "      \"type\": \"PAYPAGE\""
			+ "  }"
			+ "}";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"SaApiException: TempletPageErrorCode_002  新增失敗，此付款頁樣式已存在\","
			+ "  \"code\": \"BUSINESS[TempletPageErrorCode_002]\","
			+ "  \"message\": \"新增失敗，此付款頁樣式已存在\","
			+ "  \"success\": false"
			+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("004修改付款頁面樣式-停用付款頁")
	void testUpdateStopPayPageTempletPage() throws Exception {
		
		var response = templetPageService.findTempletPageById(PAYPAGE_TEST_ID);
		String previousData = objectMapper.writeValueAsString(response);
		previousData = StringTool.escapeChineseToDB(previousData);
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + previousData + "\","
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(PAYPAGE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='TEST' && @.templetId=='JY001' && @.status=='NO')]").exists());
	}
	
	@Test
	@DisplayName("005修改付款頁面樣式-啟用付款頁")
	void testUpdateActivatePayPageTempletPage() throws Exception {
		
		var response = templetPageService.findTempletPageById(PAYPAGE_TEST_ID);
		String previousData = objectMapper.writeValueAsString(response);
		previousData = StringTool.escapeChineseToDB(previousData);
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + previousData + "\","
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  },"
				+"   \"status\": \"YES\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(PAYPAGE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='TEST' && @.templetId=='JY001' && @.status=='YES')]").exists());
		
		templetPageService.deleteTempletPage(PAYPAGE_TEST_ID);
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(PAYPAGE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='TEST' && @.templetId=='JY001')]").doesNotExist());
	}
	
	@Test
	@DisplayName("006修改付款頁面樣式-ID不存在")
	void testUpdatePayPageTempletPageNotExist() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  },"
				+"   \"status\": \"YES\""
				+ "}";
		
		String responseJson = 
				"{"
				+ "  \"data\": \"SaApiException: TempletPageErrorCode_003  修改失敗，此付款頁樣式不存在\","
				+ "  \"code\": \"BUSINESS[TempletPageErrorCode_003]\","
				+ "  \"message\": \"修改失敗，此付款頁樣式不存在\","
				+ "  \"success\": false"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("007查詢付款頁面樣式-方便付")
	void testGetAllEZPTempletPage() throws Exception {
		
		templetPageService.deleteTempletPage(EZP_TEST_ID);
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(EZP_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.size").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].country").value(everyItem(equalTo(""))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].tempetId").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].updateTime").value(everyItem(isValidTimeFormat())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].status").value(everyItem(anyOf(equalTo("NO"), equalTo("YES")))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[*].tempetId").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.number").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.sorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.unsorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.sort.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.numberOfElements").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.first").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.sorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.unsorted").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.sort.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageSize").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.pageNumber").value(instanceOf(Integer.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.paged").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.unpaged").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.last").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.empty").value(instanceOf(Boolean.class)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='' && @.templetId=='JY001')]").doesNotExist());
	}
	
	@Test
	@DisplayName("008新增付款頁面樣式-方便付")
	void testCreateEZPTempletPage() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"EZP\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(CREATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(EZP_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='' && @.templetId=='JY001' && @.status=='YES')]").exists());
	}
	
	@Test
	@DisplayName("009修改付款頁面樣式-停用方便付")
	void testUpdateStopEZPTempletPage() throws Exception {
		
		var response = templetPageService.findTempletPageById(EZP_TEST_ID);
		String previousData = objectMapper.writeValueAsString(response);
		previousData = StringTool.escapeChineseToDB(previousData);
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + previousData + "\","
				+ "  \"id\": {"
				+ "      \"country\": \"\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(EZP_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='' && @.templetId=='JY001' && @.status=='NO')]").exists());
	}
	
	@Test
	@DisplayName("010修改付款頁面樣式-啟用方便付")
	void testUpdateActivateEZPTempletPage() throws Exception {
		
		var response = templetPageService.findTempletPageById(EZP_TEST_ID);
		String previousData = objectMapper.writeValueAsString(response);
		previousData = StringTool.escapeChineseToDB(previousData);
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + previousData + "\","
				+ "  \"id\": {"
				+ "      \"country\": \"\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"YES\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(UPDATE_SUCCESSFUL_RESPONSE_JSON));
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(EZP_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='' && @.templetId=='JY001' && @.status=='YES')]").exists());
		
		templetPageService.deleteTempletPage(EZP_TEST_ID);
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(EZP_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.data.content[?(@.country=='' && @.templetId=='JY001')]").doesNotExist());
	}
	
	@Test
	@DisplayName("011查詢付款頁面樣式-page帶負數")
	void testGetTempletPagePageNegativePage() throws Exception {
		
		String uri = EZP_URL + "?page=-1&size=10";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"IllegalArgumentException: Page index must not be less than zero\","
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
	@DisplayName("012查詢付款頁面樣式-size帶0")
	void testGetTempletPagePageSizeZero() throws Exception {
		
		String uri = EZP_URL + "?page=0&size=0";
		
		String responseJson = 
			"{"
			+ "  \"data\": \"IllegalArgumentException: Page size must not be less than one\","
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
	@DisplayName("013查詢付款頁面樣式-type帶OTHER")
	void testGetTempletPageOther() throws Exception {
		
		String uri = TEMPLET_PAGE_GET_URL.replace("{type}", "OTHER");
		
		String responseJson = 
			"{"
			+ "  \"data\": \"IllegalArgumentException: No enum constant com.hitrsutpay.sa.backend.enums.TempletPageType.OTHER\","
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
	@DisplayName("014新增付款頁面樣式-參數帶null")
	void testCreateTempletPageNull() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": null,"
				+ "      \"templetId\": null,"
				+ "      \"type\": null"
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.type:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:must not be blank")));
	}
	
	@Test
	@DisplayName("015新增付款頁面樣式-id帶null")
	void testCreateTempletPageIdNull() throws Exception {
		
		
		String requestJson = 
				"{"
				+ "  \"id\": null"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id:must not be null")));
	}
	
	@Test
	@DisplayName("016新增付款頁面樣式-templetId帶空字串")
	void testCreateTempletPageBlank() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \" \","
				+ "      \"type\": \"PAYPAGE\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:must not be blank")));
	}
	
	@Test
	@DisplayName("017新增付款頁面樣式-oversize")
	void testCreateTempletPageOverSize() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"01234567890\","
				+ "      \"templetId\": \"01234567890\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:size must be between 0 and 10")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:size must be between 1 and 10")));
	}
	
	@Test
	@DisplayName("018新增付款頁面樣式-非英數字")
	void testCreateTempletPageNonAlphanumericCharacters() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"!\","
				+ "      \"templetId\": \"!\","
				+ "      \"type\": \"PAYPAGE\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:Must contain only alphanumeric characters or be empty")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:Must contain only alphanumeric characters")));
	}
	
	@Test
	@DisplayName("019新增付款頁面樣式-other")
	void testCreateTempletPageOther() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"OTHER\""
				+ "  }"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("InvalidFormatException: Cannot deserialize value of type `com.hitrsutpay.sa.backend.enums.TempletPageType` from String \"OTHER\": not one of the values accepted for Enum class: [EZP, PAYPAGE]")));
	}
	
	@Test
	@DisplayName("020新增付款頁面樣式-方便付帶country值")
	void testCreateEZPTempletPageWithCountry() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"EZP\""
				+ "  }"
				+ "}";
		
		String responseJson = 
				"{"
				+ "  \"data\": \"SaApiException: TempletPageErrorCode_001  新增失敗，方便付的國家欄位必須為空字串\","
				+ "  \"code\": \"BUSINESS[TempletPageErrorCode_001]\","
				+ "  \"message\": \"新增失敗，方便付的國家欄位必須為空字串\","
				+ "  \"success\": false"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("021修改付款頁面樣式-參數帶null")
	void testUpdateTempletPageNull() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": null,"
				+ "  \"id\": {"
				+ "      \"country\": null,"
				+ "      \"templetId\": null,"
				+ "      \"type\": null"
				+ "  },"
				+"   \"status\": null"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("status:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.type:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:must not be blank")));
	}
	
	@Test
	@DisplayName("022修改付款頁面樣式-id帶null")
	void testUpdateTempletPageIdNull() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \" \","
				+ "  \"id\": null,"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id:must not be null")));
	}
	
	@Test
	@DisplayName("023修改付款頁面樣式-String參數帶空字串")
	void testUpdateTempletPageBlank() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \" \","
				+ "  \"id\": {"
				+ "      \"country\": \"\","
				+ "      \"templetId\": \" \","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:Must contain only alphanumeric characters")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:must not be blank")));
	}
	
	@Test
	@DisplayName("024修改付款頁面樣式-OverSize")
	void testUpdateTempletPageOverSize() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"id\": {"
				+ "      \"country\": \"01234567890\","
				+ "      \"templetId\": \"01234567890\","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:size must be between 0 and 10")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:size must be between 1 and 10")));
	}
	
	@Test
	@DisplayName("025修改付款頁面樣式-非英數字")
	void testUpdateTempletPageNonAlphanumericCharacters() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"id\": {"
				+ "      \"country\": \"!\","
				+ "      \"templetId\": \"!\","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.country:Must contain only alphanumeric characters or be empty")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("id.templetId:Must contain only alphanumeric characters")));
	}
	
	@Test
	@DisplayName("026修改付款頁面樣式-type帶OTHER")
	void testUpdateTempletPageTypeOther() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"OTHER\""
				+ "  },"
				+"   \"status\": \"NO\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("InvalidFormatException: Cannot deserialize value of type `com.hitrsutpay.sa.backend.enums.TempletPageType` from String \"OTHER\": not one of the values accepted for Enum class: [EZP, PAYPAGE]")));
	}
	
	@Test
	@DisplayName("027修改付款頁面樣式-status帶OTHER")
	void testUpdateTempletPageStatusOther() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"id\": {"
				+ "      \"country\": \"TEST\","
				+ "      \"templetId\": \"JY001\","
				+ "      \"type\": \"EZP\""
				+ "  },"
				+"   \"status\": \"OTHER\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(TEMPLET_PAGE_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("InvalidFormatException: Cannot deserialize value of type `com.hitrsutpay.sa.backend.enums.TwoFactor` from String \"OTHER\": not one of the values accepted for Enum class: [NO, YES]")));
	}
}
