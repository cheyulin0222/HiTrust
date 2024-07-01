package com.hitrsutpay.sa.backend.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.req.SystemSettingUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.SystemSettingResponse;
import com.hitrsutpay.sa.backend.service.SystemSettingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * TP_CONFIG表格中，對TPID為TRUSTPAY這筆資料的查詢及修改
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="System Setting(系統設定) API")
public class SystemSettingController {

	private final SystemSettingService systemSettingService;
	public static final String SYSTEM_SETTING_GET_URL = "/get-system-setting";
	public static final String SYSTEM_SETTING_UPDATE_URL = "/update-system-setting";

	/**
	 * 查詢TPID為TRUSTPAY的系統設定資料
	 * @return
	 */
	@GetMapping(value=SYSTEM_SETTING_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢系統設定", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<SystemSettingResponse> getSystemSetting() {
		log.info("getSystemSetting");
		var result = systemSettingService.getSystemSetting();
		return new ResponseModel<SystemSettingResponse>(result);
	}
	
	/**
	 * 修改TPID為TRUSTPAY的系統設定資料
	 * @param request
	 * @return
	 */
	@PostMapping(value=SYSTEM_SETTING_UPDATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "修改系統設定", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> updateSystemSetting(@Valid @RequestBody SystemSettingUpdateRequest request) {
		log.info("updateSystemSetting");
		var result = systemSettingService.updateSystemSetting(request);
		return new ResponseModel<MessageResponse>(result);
	}
}
