package com.hitrsutpay.sa.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.TestCardErrorCode;
import com.hitrsutpay.sa.backend.req.TestCardCreateRequest;
import com.hitrsutpay.sa.backend.req.TestCardUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.TestCardQueryResponse;
import com.hitrsutpay.sa.backend.service.TestCardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 測試卡號(B2BCREDITLOG資料表)查詢，修改，新增
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Test Card(測試卡號管理) API")
public class TestCardController {
	
	public static final String TEST_CARD_DATA_GET_URL = "/get-test-card-data/{cardNumber}";
	public static final String TEST_CARD_DATA_CREATE_URL = "/create-test-card-data";
	public static final String TEST_CARD_DATA_UPDATE_URL = "/update-test-card-data";
	
	private final TestCardService testCardService;

	/**
	 * 以測試卡號取得相關資料
	 * @param testCardQueryRequest
	 * @return
	 */
	@GetMapping(value = TEST_CARD_DATA_GET_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "查詢測試卡號", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<TestCardQueryResponse> getTestCardData(@PathVariable String cardNumber) {
		log.info("TestCardController.getTestCardData");
		// 卡號必須為15~16位數字
		if (!cardNumber.matches("^[0-9]{15,16}$")) {
			throw new SaApiException(TestCardErrorCode._005);
		}
		// 取得測試卡號資料
		var result = testCardService.getTestCardbyCreditCardNum(cardNumber);
		// 回傳結果
		return new ResponseModel<TestCardQueryResponse>(result);
	}

	/**
	 * 新增測試卡號資訊
	 * @param request
	 * @param authentication
	 * @return
	 */
	@PostMapping(value = TEST_CARD_DATA_CREATE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "新增測試卡號", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> createTestCardData(@Valid @RequestBody TestCardCreateRequest request , Authentication authentication) {
		log.info("TestCardController.createTestCardData");
		
		// 取得userid
		var user = authentication.getName();
		var userid = user.split("@")[0];
		// 新增測試卡號
		var result = testCardService.createTestCardData(request, userid);
		// 回傳新增結果
		return new ResponseModel<MessageResponse>(result);
	}

	/**
	 * 修改測試卡號資訊
	 * @param testCardUpdateRequest
	 * @param authentication
	 * @return
	 */
	@PostMapping(value = TEST_CARD_DATA_UPDATE_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
	@Operation(summary = "修改測試卡號", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> updateTestCardData(@RequestBody @Valid TestCardUpdateRequest testCardUpdateRequest, Authentication authentication) {
		log.info("TestCardController.updateTestCardData");
		
		// 取得userid
		var user = authentication.getName();
		var userid = user.split("@")[0];
		// 修改測試卡號資訊
		var result = testCardService.updateTestCardData(testCardUpdateRequest, userid);
		// 回傳修改結果
		return new ResponseModel<MessageResponse>(result);
	}
}
