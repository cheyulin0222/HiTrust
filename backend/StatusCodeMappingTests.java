package com.hitrsutpay.sa.backend;


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
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;

import com.hitrsutpay.sa.backend.constant.TestConstant;
import com.hitrsutpay.sa.backend.controller.StatusCodeMappingController;
import com.hitrsutpay.sa.backend.enums.ErrorType;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.security.JwtUtils;
import com.hitrsutpay.sa.backend.security.constant.JwtConstant;
import com.hitrsutpay.sa.backend.service.StatusCodeMappingService;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class StatusCodeMappingTests {

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
	private StatusCodeMappingService statusCodeMappingService;
	
	
	@Test
	@DisplayName("001初始化資料庫狀態-若測試資料存在則刪除")
	void init() throws Exception {
		
		Integer idJtest = statusCodeMappingService.getStatusCodeMappingIdByCode("JTEST");
		Integer idFtest = statusCodeMappingService.getStatusCodeMappingIdByCode("FTEST");
		
		if (idJtest != null) {
			statusCodeMappingService.deleteStatusCodeMapping(idJtest);
		}
		if (idFtest != null) {
			statusCodeMappingService.deleteStatusCodeMapping(idFtest);
		}
	}
	
	@Test
	@DisplayName("002查詢狀態碼對應-檢核查詢資料及確認新增資料是否存在")
	void testGetStatusCodeMapping() throws Exception {
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(StatusCodeMappingController.STATUS_CODE_MAPPING_GET_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseModel.SUCCESS_CODE))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusId").value(everyItem(instanceOf(Number.class))))		
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusCode").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusDesc").value(everyItem(instanceOf(String.class))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusCode").value(not(hasItem("JTEST"))));
	}
	
	@Test
	@DisplayName("003新增狀態碼對應")
	void testCreateStatusCodeMapping() throws Exception {
		String requestJson = 
				"{"
				+ "  \"statusCode\": \"JTEST\","
				+ "  \"statusDesc\": \"哈哈哈\""
				+ "}";
		
		String responseJson = TestConstant.CREATE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("004查詢狀態碼對應-檢核是否新增成功")
	void testGetStatusCodeMappingCreate() throws Exception {
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(StatusCodeMappingController.STATUS_CODE_MAPPING_GET_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.statusCode=='JTEST' && @.statusDesc=='哈哈哈')]").exists());	
	}
	
	@Test
	@DisplayName("005修改狀態碼對應")
	void testUpdateStatusCodeMapping() throws Exception {
		
		Integer id = statusCodeMappingService.getStatusCodeMappingIdByCode("JTEST");
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{\\\"statusId\\\":" + id + ",\\\"statusCode\\\":\\\"JTEST\\\",\\\"statusDesc\\\":\\\"哈哈哈\\\"}\","
				+ "  \"statusId\":" + id + ","
				+ "  \"statusCode\": \"JTEST\","
				+ "  \"statusDesc\": \"呵呵呵\""
				+ "}";
		
		String responseJson = TestConstant.UPDATE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("006查詢狀態碼對應-修改檢核")
	void testGetStatusCodeMappingUpdate() throws Exception {
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(StatusCodeMappingController.STATUS_CODE_MAPPING_GET_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[?(@.statusCode=='JTEST' && @.statusDesc=='呵呵呵')]").exists());	
	}
	
	@Test
	@DisplayName("007新增狀態碼對應-重複的狀態碼")
	void testReCreateStatusCodeMapping() throws Exception {
		String requestJson = 
				"{"
				+ "  \"statusCode\": \"JTEST\","
				+ "  \"statusDesc\": \"哈哈哈\""
				+ "}";
		
		String responseJson =
				"{"
				+ "  \"data\": \"SaApiException: StatusCodeMapingErrorCode_001  新增失敗，重複的狀態碼\","
				+ "  \"code\": \"BUSINESS[StatusCodeMapingErrorCode_001]\","
				+ "  \"message\": \"新增失敗，重複的狀態碼\","
				+ "  \"success\": false"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_CREATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("008修改狀態碼對應-重複的狀態碼")
	void testUpdateStatusCodeMappingCreated() throws Exception {
		
		statusCodeMappingService.createStatusCodeMapping("FTEST", "嘻嘻");
		Integer id = statusCodeMappingService.getStatusCodeMappingIdByCode("JTEST");
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{\\\"statusId\\\":" + id + ",\\\"statusCode\\\":\\\"JTEST\\\",\\\"statusDesc\\\":\\\"呵呵呵\\\"}\","
				+ "  \"statusId\":" + id + ","
				+ "  \"statusCode\": \"FTEST\","
				+ "  \"statusDesc\": \"呵呵呵\""
				+ "}";
		
		String responseJson = 
				"{"
				+ "  \"data\": \"SaApiException: StatusCodeMapingErrorCode_004  修改失敗，重複的狀態碼\","
				+ "  \"code\": \"BUSINESS[StatusCodeMapingErrorCode_004]\","
				+ "  \"message\": \"修改失敗，重複的狀態碼\","
				+ "  \"success\": false"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("009刪除狀態碼對應-JTEST")
	void testDeleteStatusCodeMappingJtest() throws Exception {
		
		Integer id = statusCodeMappingService.getStatusCodeMappingIdByCode("JTEST");
		
		String uri = StatusCodeMappingController.STATUS_CODE_MAPPING_DELETE_URL;
		uri = uri.replace("{id}", String.valueOf(id));
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{\\\"statusId\\\":" + id + ",\\\"statusCode\\\":\\\"JTEST\\\",\\\"statusDesc\\\":\\\"呵呵呵\\\"}\""
				+ "}";
		
		String responseJson = TestConstant.DELETE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.delete(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("010刪除狀態碼對應-FTEST")
	void testDeleteStatusCodeMappingFtest() throws Exception {
		
		Integer id = statusCodeMappingService.getStatusCodeMappingIdByCode("FTEST");
		
		String uri = StatusCodeMappingController.STATUS_CODE_MAPPING_DELETE_URL;
		uri = uri.replace("{id}", String.valueOf(id));
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{\\\"statusId\\\":" + id + ",\\\"statusCode\\\":\\\"FTEST\\\",\\\"statusDesc\\\":\\\"嘻嘻\\\"}\""
				+ "}";
		
		String responseJson = TestConstant.DELETE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.delete(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("011查詢狀態碼對應-刪除檢核")
	void testGetStatusCodeMappingDelete() throws Exception {
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.get(StatusCodeMappingController.STATUS_CODE_MAPPING_GET_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusCode").value(not(hasItem("JTEST"))))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data[*].statusCode").value(not(hasItem("FTEST"))));
	}
	
	@Test
	@DisplayName("012新增狀態碼對應-String空白檢核")
	void testCreateStatusCodeMappingBlank() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"statusCode\": \" \","
				+ "  \"statusDesc\": \" \""
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(StatusCodeMappingController.STATUS_CODE_MAPPING_CREATE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusCode:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusDesc:must not be blank")));
	}
	
	@Test
	@DisplayName("013新增狀態碼對應-參數null檢核")
	void testCreateStatusCodeMappingNull() throws Exception {
		
		String requestJson = 
			"{"
			+ "  \"statusCode\": null,"
			+ "  \"statusDesc\": null"
			+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(StatusCodeMappingController.STATUS_CODE_MAPPING_CREATE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusCode:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusDesc:must not be blank")));
	}
	
	
	@Test
	@DisplayName("014修改狀態碼對應-參數null檢核")
	void testUpdateStatusCodeMappingNull() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": null,"
				+ "  \"statusId\": null,"
				+ "  \"statusCode\": null,"
				+ "  \"statusDesc\": null"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusId:must not be null")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusCode:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusDesc:must not be blank")));
	}
	
	@Test
	@DisplayName("015修改狀態碼對應-String空白檢核")
	void testUpdateStatusCodeMappingBlank() throws Exception {
		
		Integer id = statusCodeMappingService.getStatusCodeMappingIdByCode("JTEST");
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \" \","
				+ "  \"statusId\":" + id + ","
				+ "  \"statusCode\": \" \","
				+ "  \"statusDesc\": \" \""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusCode:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("statusDesc:must not be blank")));
	}
	
	@Test
	@DisplayName("016修改狀態碼對應-狀態碼id為負數")
	void testUpdateStatusCodeMappingNegative() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"statusId\": -1,"
				+ "  \"statusCode\": \"AA\","
				+ "  \"statusDesc\": \"BB\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("statusId:must be greater than or equal to 0"));
	}
	
	@Test
	@DisplayName("017修改狀態碼對應-狀態碼id為5位數")
	void testUpdateStatusCodeMappingTenThousand() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"statusId\": 10000,"
				+ "  \"statusCode\": \"AA\","
				+ "  \"statusDesc\": \"BB\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("statusId:must be less than or equal to 9999"));
	}
	
	@Test
	@DisplayName("018修改狀態碼對應-狀態碼id不存在")
	void testUpdateStatusCodeMappingIdNotExists() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{}\","
				+ "  \"statusId\": 9999,"
				+ "  \"statusCode\": \"AA\","
				+ "  \"statusDesc\": \"BB\""
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.post(StatusCodeMappingController.STATUS_CODE_MAPPING_UPDATE_URL)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(requestJson)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BUSINESS[StatusCodeMapingErrorCode_002]"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("修改失敗，ID不存在"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("SaApiException: StatusCodeMapingErrorCode_002  修改失敗，ID不存在"));
	}
	
	@Test
	@DisplayName("019刪除狀態碼對應-狀態碼id為負數")
	void testDeleteStatusCodeMappingNegative() throws Exception {
		
		String uri = StatusCodeMappingController.STATUS_CODE_MAPPING_DELETE_URL;
		uri = uri.replace("{id}", String.valueOf(-1));
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.delete(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(TestConstant.EMPTY_PREVIOUS_DATA)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.SYSTEM.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.SYSTEM.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("ConstraintViolationException: deleteStatusCodeMapping.id: must be greater than or equal to 0"));
	}
	
	
	@Test
	@DisplayName("020刪除狀態碼對應-狀態碼id不存在")
	void testDeleteStatusCodeMappingNotExist() throws Exception {
		
		String uri = StatusCodeMappingController.STATUS_CODE_MAPPING_DELETE_URL;
		uri = uri.replace("{id}", String.valueOf(9999));
		
		String responseJson = 
				"{"
				+ "  \"data\": \"SaApiException: StatusCodeMapingErrorCode_003  刪除失敗，ID不存在\","
				+ "  \"code\": \"BUSINESS[StatusCodeMapingErrorCode_003]\","
				+ "  \"message\": \"刪除失敗，ID不存在\","
				+ "  \"success\": false"
				+ "}";
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.delete(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(TestConstant.EMPTY_PREVIOUS_DATA)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("021刪除狀態碼對應-狀態碼id為5位數")
	void testDeleteStatusCodeMappingTenThousand() throws Exception {
		
		String uri = StatusCodeMappingController.STATUS_CODE_MAPPING_DELETE_URL;
		uri = uri.replace("{id}", String.valueOf(10000));
		
		mockMvc.perform(
			MockMvcRequestBuilders
			.delete(uri)
			.header(JwtConstant.TOKEN_HEADER, TOKEN)
			.content(TestConstant.EMPTY_PREVIOUS_DATA)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is5xxServerError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.SYSTEM.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.SYSTEM.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value("ConstraintViolationException: deleteStatusCodeMapping.id: must be less than or equal to 9999"));
	}
}
