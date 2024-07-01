package com.hitrsutpay.sa.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.req.DeleteRequest;
import com.hitrsutpay.sa.backend.req.StatusCodeMappingCreateRequest;
import com.hitrsutpay.sa.backend.req.StatusCodeMappingUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.StatusCodeMappingResponse;
import com.hitrsutpay.sa.backend.service.StatusCodeMappingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * 狀態碼對應系統
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="StatusCodeMapping(狀態碼對應系統) API")
public class StatusCodeMappingController {

	public static final String STATUS_CODE_MAPPING_GET_URL = "/get-status-code-mapping";
	public static final String STATUS_CODE_MAPPING_CREATE_URL = "/create-status-code-mapping";
	public static final String STATUS_CODE_MAPPING_UPDATE_URL = "/update-status-code-mapping";
	public static final String STATUS_CODE_MAPPING_DELETE_URL = "/delete-status-code-mapping/{id}";
	
	private final StatusCodeMappingService mappingService;
	
	/**
	 * 查詢所有狀態碼對應
	 * @return
	 */
	@GetMapping(value = STATUS_CODE_MAPPING_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢狀態碼對應", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<List<StatusCodeMappingResponse>> getStatusCodeMapping() {
		log.info("getStatusCodeMapping");
		var result = mappingService.getStatusCodeMapping();
		return new ResponseModel<List<StatusCodeMappingResponse>>(result);
	}
	
	/**
	 * 以狀態碼、狀態碼描述進行新增狀態碼
	 * @param request
	 * @return
	 */
	@PostMapping(value = STATUS_CODE_MAPPING_CREATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "新增狀態碼對應", security= @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> createStatusCodeMapping(@Valid @RequestBody StatusCodeMappingCreateRequest request) {
		log.info("createStatusCodeMapping");
		var result = mappingService.createStatusCodeMapping(request.getStatusCode(), request.getStatusDesc());
		return new ResponseModel<MessageResponse>(result);
	}
	
	/**
	 * 以id、狀態碼、狀態碼描述進行修改狀態碼
	 * @param request
	 * @return
	 */
	@PostMapping(value = STATUS_CODE_MAPPING_UPDATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "修改狀態碼對應", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> updateStatusCodeMapping(@Valid @RequestBody StatusCodeMappingUpdateRequest request) {
		log.info("updateStatusCodeMapping");
		var result = mappingService.updateStatusCodeMapping(request);
		return new ResponseModel<MessageResponse>(result);
	}
	
	/**
	 * 以id進行狀態碼刪除
	 * @param id
	 * @param request
	 * @return
	 */
	@DeleteMapping(value = STATUS_CODE_MAPPING_DELETE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "刪除狀態碼對應", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> deleteStatusCodeMapping(@PathVariable @NotNull @Min(0) @Max(9999) Integer id, @RequestBody DeleteRequest request) {
		log.info("deleteStatusCodeMapping");
		var result = mappingService.deleteStatusCodeMapping(id);
		return new ResponseModel<MessageResponse>(result);
	}
	
}
