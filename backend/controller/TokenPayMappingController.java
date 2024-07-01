package com.hitrsutpay.sa.backend.controller;



import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.MerchantErrorCode;
import com.hitrsutpay.sa.backend.req.TokenPayMappingSaveRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.TokenPayMappingResponse;
import com.hitrsutpay.sa.backend.service.TokenPayMappingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 速速付Token授權
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="TokenPay Mapping (速速付Token授權) API")
public class TokenPayMappingController {

	public static final String TOKENPAY_MAPPING_GET_URL = "/get-tokenpay-store-authorization-data/{storeId}";
	public static final String TOKENPAY_MAPPING_UPDATE_URL = "/update-tokenpay-store-authorization-data";
	
	private final TokenPayMappingService mappingService;
	
	/**
	 * 透過storeid
	 * 查詢商代速速付Token授權狀態
	 * @param storeId
	 * @return
	 */
	@GetMapping(value = TOKENPAY_MAPPING_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢速速付對應", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<List<TokenPayMappingResponse>> getTokenPayMapping(@PathVariable @NotBlank @Size(min = 1, max = 10) String storeId) {
		log.info("getThreedRedirectModeOptions");
		if (!storeId.matches("^[a-zA-Z0-9]*$")) {
			throw new SaApiException(MerchantErrorCode._002);
		}
		var result = mappingService.getTokenPayMapping(storeId);
		return new ResponseModel<List<TokenPayMappingResponse>>(result);
	}
	
	/**
	 * 透過授權商代、被授權商代、狀態
	 * 進行新增、修改速速付授權狀態
	 * @param request
	 * @param authentication
	 * @return
	 */
	@PostMapping(value = TOKENPAY_MAPPING_UPDATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "儲存速速付對應", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> saveTokenPayMapping(@Valid @RequestBody TokenPayMappingSaveRequest request, Authentication authentication) {
		log.info("saveTokenPayMapping");
		var userid = authentication.getName();
		var result = mappingService.saveTokenPayMapping(request, userid);
		return new ResponseModel<MessageResponse>(result);
	}
}
