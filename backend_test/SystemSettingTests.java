package com.hitrsutpay.sa.backend;


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
import com.hitrsutpay.sa.backend.constant.TestConstant;
import com.hitrsutpay.sa.backend.controller.SystemSettingController;
import com.hitrsutpay.sa.backend.enums.ErrorType;
import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.TempletPageErrorCode;
import com.hitrsutpay.sa.backend.security.JwtUtils;
import com.hitrsutpay.sa.backend.security.constant.JwtConstant;
import com.hitrsutpay.sa.backend.service.SystemSettingService;
import com.hitrsutpay.sa.backend.utils.StringTool;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Slf4j
public class SystemSettingTests {
	
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
	private SystemSettingService sysSettingService;
	
	private static String original_flag;
	private static String original_systemSetting;
	
	@Test
	@DisplayName("001修改系統設定")
	void testUpdateSystemSettingFirst() throws Exception {
		
		var systemSettingResponse = sysSettingService.getSystemSetting();
		
		original_systemSetting = objectMapper.writeValueAsString(systemSettingResponse);
		original_systemSetting = StringTool.escapeChineseToDB(original_systemSetting);
		original_flag = systemSettingResponse.getThreedRedirectModeFlag().name();
		
		log.info("testUpdateSystemSettingFirst: ORIGINAL_SYSTEMSETTING={}", original_systemSetting);
		log.info("testUpdateSystemSettingFirst: ORIGINAL_FLAG={}", original_flag);
		
		String updateFlag = "OPEN".equals(original_flag)? "CLOSED" : "OPEN";
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + original_systemSetting + "\","
				+ "  \"tpopEmail\": \"A\","
				+ "  \"smtpHost\": \"A\","
				+ "  \"payPage\": \"A\","
				+ "  \"errPage\": \"A\","
				+ "  \"tpKey\": \"A\","
				+ "  \"ibmPm\": \"A\","
				+ "  \"set\": \"A\","
				+ "  \"trace\": \"A\","
				+ "  \"log\": \"A\","
				+ "  \"bypass\": \"A\","
				+ "  \"pmHost\": \"A\","
				+ "  \"pmPort\": \"A\","
				+ "  \"pmId\": \"A\","
				+ "  \"pmPwd\": \"A\","
				+ "  \"pmSSL\": \"A\","
				+ "  \"visa3DPage\": \"A\","
				+ "  \"threedRedirectModeFlag\": \"" + updateFlag + "\""
				+ "}";
		
			String responseJson = TestConstant.UPDATE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("002查詢系統設定-驗證修改結果")
	void testGetSystemSettingFirst() throws Exception {
		
		String dbFlag = "OPEN".equals(original_flag)? "CLOSED" : "OPEN";
		
		String responseJson = 
				"{"
				+ "  \"data\": {"
				+ "    \"tpId\": \"TRUSTPAY\","
				+ "    \"tpopEmail\": \"A\","
				+ "    \"smtpHost\": \"A\","
				+ "    \"payPage\": \"A\","
				+ "    \"errPage\": \"A\","
				+ "    \"tpKey\": \"A\","
				+ "    \"ibmPm\": \"A\","
				+ "    \"set\": \"A\","
				+ "    \"trace\": \"A\","
				+ "    \"log\": \"A\","
				+ "    \"bypass\": \"A\","
				+ "    \"pmHost\": \"A\","
				+ "    \"pmPort\": \"A\","
				+ "    \"pmId\": \"A\","
				+ "    \"pmPwd\": \"A\","
				+ "    \"pmSSL\": \"A\","
				+ "    \"visa3DPage\": \"A\","
				+ "    \"threedRedirectModeFlag\": \"" + dbFlag + "\""
				+ "  },"
				+ "  \"code\": \"0000\","
				+ "  \"message\": \"\","
				+ "  \"success\": true"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(SystemSettingController.SYSTEM_SETTING_GET_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("003修改系統設定-還原系統設定")
	void testUpdateSystemSettingSecond() throws Exception {
		
		String dbFlag = "OPEN".equals(original_flag)? "CLOSED" : "OPEN";
		String updateFlag = "OPEN".equals(original_flag)? "OPEN" : "CLOSED";
		
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"{\\\"tpId\\\":\\\"TRUSTPAY\\\",\\\"tpopEmail\\\":\\\"A\\\",\\\"smtpHost\\\":\\\"A\\\",\\\"payPage\\\":\\\"A\\\",\\\"errPage\\\":\\\"A\\\",\\\"tpKey\\\":\\\"A\\\",\\\"ibmPm\\\":\\\"A\\\",\\\"set\\\":\\\"A\\\",\\\"trace\\\":\\\"A\\\",\\\"log\\\":\\\"A\\\",\\\"bypass\\\":\\\"A\\\",\\\"pmHost\\\":\\\"A\\\",\\\"pmPort\\\":\\\"A\\\",\\\"pmId\\\":\\\"A\\\",\\\"pmPwd\\\":\\\"A\\\",\\\"pmSSL\\\":\\\"A\\\",\\\"visa3DPage\\\":\\\"A\\\",\\\"threedRedirectModeFlag\\\":\\\"" + dbFlag + "\\\"}\","
				+ "  \"tpopEmail\": \"paymentservice@hitrust.com.tw\","
				+ "  \"smtpHost\": \"mail.hitrust.com.tw\","
				+ "  \"payPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/Payres\","
				+ "  \"errPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/error/err.jsp\","
				+ "  \"tpKey\": \"GC00000001.der\","
				+ "  \"ibmPm\": \"0\","
				+ "  \"set\": \"0\","
				+ "  \"trace\": \"1\","
				+ "  \"log\": \"1\","
				+ "  \"bypass\": \"0\","
				+ "  \"pmHost\": \"203.75.242.17\","
				+ "  \"pmPort\": \"80\","
				+ "  \"pmId\": \"admin\","
				+ "  \"pmPwd\": \"admin\","
				+ "  \"pmSSL\": \"0\","
				+ "  \"visa3DPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/TXController\","
				+ "  \"threedRedirectModeFlag\": \"" + updateFlag + "\""
				+ "}";
		
		String responseJson = TestConstant.UPDATE_SUCCESSFUL_RESPONSE_JSON;
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("004查詢系統設定-驗證還原結果")
	void testGetSystemSettingSecond() throws Exception {
		
		String dbFlag = "OPEN".equals(original_flag)? "OPEN" : "CLOSED";
		
		String responseJson = 
				"{"
				+ "  \"data\": {"
				+ "    \"tpId\": \"TRUSTPAY\","
				+ "    \"tpopEmail\": \"paymentservice@hitrust.com.tw\","
				+ "    \"smtpHost\": \"mail.hitrust.com.tw\","
				+ "    \"payPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/Payres\","
				+ "    \"errPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/error/err.jsp\","
				+ "    \"tpKey\": \"GC00000001.der\","
				+ "    \"ibmPm\": \"0\","
				+ "    \"set\": \"0\","
				+ "    \"trace\": \"1\","
				+ "    \"log\": \"1\","
				+ "    \"bypass\": \"0\","
				+ "    \"pmHost\": \"203.75.242.17\","
				+ "    \"pmPort\": \"80\","
				+ "    \"pmId\": \"admin\","
				+ "    \"pmPwd\": \"admin\","
				+ "    \"pmSSL\": \"0\","
				+ "    \"visa3DPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/TXController\","
				+ "    \"threedRedirectModeFlag\": \"" + dbFlag + "\""
				+ "  },"
				+ "  \"code\": \"0000\","
				+ "  \"message\": \"\","
				+ "  \"success\": true"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.get(SystemSettingController.SYSTEM_SETTING_GET_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(responseJson));
	}
	
	@Test
	@DisplayName("005修改系統設定-String皆帶null")
	void testUpdateSystemSettingNull() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": null,"
				+ "  \"tpopEmail\": null,"
				+ "  \"smtpHost\": null,"
				+ "  \"payPage\": null,"
				+ "  \"errPage\": null,"
				+ "  \"tpKey\": null,"
				+ "  \"ibmPm\": null,"
				+ "  \"set\": null,"
				+ "  \"trace\": null,"
				+ "  \"log\": null,"
				+ "  \"bypass\": null,"
				+ "  \"pmHost\": null,"
				+ "  \"pmPort\": null,"
				+ "  \"pmId\": null,"
				+ "  \"pmPwd\": null,"
				+ "  \"pmSSL\": null,"
				+ "  \"visa3DPage\": null,"
				+ "  \"threedRedirectModeFlag\": null"
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, TOKEN)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpopEmail:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("smtpHost:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("payPage:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("errPage:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpKey:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("ibmPm:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("set:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("trace:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("log:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("bypass:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmHost:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPort:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPwd:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmSSL:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("visa3DPage:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("threedRedirectModeFlag:must not be null")));
	}
	
	@Test
	@DisplayName("006修改系統設定-3DFlag帶其他值")
	void testUpdateSystemSettingThreedFlagWithOtherString() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + original_systemSetting + "\","
				+ "  \"smtpHost\": \"mail.hitrust.com.tw\","
				+ "  \"payPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/Payres\","
				+ "  \"errPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/error/err.jsp\","
				+ "  \"tpKey\": \"GC00000001.der\","
				+ "  \"ibmPm\": \"0\","
				+ "  \"set\": \"0\","
				+ "  \"trace\": \"1\","
				+ "  \"log\": \"1\","
				+ "  \"bypass\": \"0\","
				+ "  \"pmHost\": \"203.75.242.17\","
				+ "  \"pmPort\": \"80\","
				+ "  \"pmId\": \"admin\","
				+ "  \"pmPwd\": \"admin\","
				+ "  \"pmSSL\": \"0\","
				+ "  \"visa3DPage\": \"https://dev-trustpay.hitrust.com.tw/TRUSTPAY/TXController\","
				+ "  \"threedRedirectModeFlag\": \"OTHER\""
				+ "}";
		
		String data = "InvalidFormatException: Cannot deserialize value of type `com.hitrsutpay.sa.backend.enums.Avalibility` from String \"OTHER\": not one of the values accepted for Enum class: [OPEN, CLOSED]";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, JwtConstant.TOKEN_PREFIX + " " + jwtToken)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString(data)));
	}
	
	@Test
	@DisplayName("007修改系統設定-String空白檢核")
	void testUpdateSystemSettingBlank() throws Exception {
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \" \","
				+ "  \"tpopEmail\": \" \","
				+ "  \"smtpHost\": \" \","
				+ "  \"payPage\": \" \","
				+ "  \"errPage\": \" \","
				+ "  \"tpKey\": \" \","
				+ "  \"ibmPm\": \" \","
				+ "  \"set\": \" \","
				+ "  \"trace\": \" \","
				+ "  \"log\": \" \","
				+ "  \"bypass\": \" \","
				+ "  \"pmHost\": \" \","
				+ "  \"pmPort\": \" \","
				+ "  \"pmId\": \" \","
				+ "  \"pmPwd\": \" \","
				+ "  \"pmSSL\": \" \","
				+ "  \"visa3DPage\": \" \","
				+ "  \"threedRedirectModeFlag\": \"OPEN\""
				+ "}";
		
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, JwtConstant.TOKEN_PREFIX + " " + jwtToken)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("previousData:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpopEmail:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("smtpHost:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("payPage:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("errPage:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpKey:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("ibmPm:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("set:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("trace:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("log:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("bypass:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmHost:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPort:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmId:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPwd:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmSSL:must not be blank")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("visa3DPage:must not be blank")));
	}
	
	@Test
	@DisplayName("008修改系統設定-String長度檢核")
	void testUpdateSystemSettingStringSize() throws Exception {
		
		String longString = "A".repeat(2049);
		
		String requestJson = 
				"{"
				+ "  \"previousData\": \"" + original_systemSetting + "\","
				+ "  \"tpopEmail\": \"" + longString + "\","
				+ "  \"smtpHost\": \"" + longString + "\","
				+ "  \"payPage\": \"" + longString + "\","
				+ "  \"errPage\": \"" + longString + "\","
				+ "  \"tpKey\": \"" + longString + "\","
				+ "  \"ibmPm\": \"" + longString + "\","
				+ "  \"set\": \"" + longString + "\","
				+ "  \"trace\": \"" + longString + "\","
				+ "  \"log\": \"" + longString + "\","
				+ "  \"bypass\": \"" + longString + "\","
				+ "  \"pmHost\": \"" + longString + "\","
				+ "  \"pmPort\": \"" + longString + "\","
				+ "  \"pmId\": \"" + longString + "\","
				+ "  \"pmPwd\": \"" + longString + "\","
				+ "  \"pmSSL\": \"" + longString + "\","
				+ "  \"visa3DPage\": \"" + longString + "\","
				+ "  \"threedRedirectModeFlag\": \"OPEN\""
				+ "}";
		
		mockMvc.perform(
				MockMvcRequestBuilders
				.post(SystemSettingController.SYSTEM_SETTING_UPDATE_URL)
				.header(JwtConstant.TOKEN_HEADER, JwtConstant.TOKEN_PREFIX + " " + jwtToken)
				.content(requestJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().is4xxClientError())
		.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorType.REQUEST.name()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ErrorType.REQUEST.getLable()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpopEmail:size must be between 1 and 50")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("smtpHost:size must be between 1 and 50")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("payPage:size must be between 1 and 100")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("errPage:size must be between 1 and 100")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("tpKey:size must be between 1 and 2048")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("ibmPm:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("set:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("trace:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("log:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("bypass:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmHost:size must be between 1 and 15")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPort:size must be between 1 and 6")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmId:size must be between 1 and 20")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmPwd:size must be between 1 and 20")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("pmSSL:size must be between 1 and 1")))
		.andExpect(MockMvcResultMatchers.jsonPath("$.data").value(Matchers.containsString("visa3DPage:size must be between 1 and 100")));
	}
	
}
