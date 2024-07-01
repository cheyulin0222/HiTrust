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

import com.hitrsutpay.sa.backend.security.JwtUtils;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.DisplayName.class)
class StoreTransactionStatisticReportTests {
	
	private final String JWT_TOKEN = JwtUtils.generateToken(
			User.builder()
			.username	("jackyhsieh@hitrust.com")
			.password	("")
			.roles		("admin")
			.build());
	
	@Autowired
	private MockMvc mockMvc;
	
	// V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V--- SA2101 開通商家數交易統計報表 ---V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V-V //
	

	@Test
	@DisplayName("001-下載開通商家數交易統計報表")
	void testDownloadStoreTransactionStatisticReport() throws Exception {
		
//		String requestJson = 
//				"{"
//		        + "  \"startTime\": \"2024-01-01\","
//		        + "  \"endTime\": \"2024-01-31\""
//		        + "}";
        
        mockMvc.perform(MockMvcRequestBuilders.get("/download-store-transaction-statistic-report") // 使用 GET 請求
                .header("Authorization", "Bearer " + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .param("startTime", "2024-01-01")
        		.param("endTime", "2024-01-31")
                //.content(requestJson)
                .accept(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}	
	
	
}
