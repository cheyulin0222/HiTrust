package com.hitrsutpay.sa.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.enums.TempletPageType;
import com.hitrsutpay.sa.backend.req.TempletPageCreateRequest;
import com.hitrsutpay.sa.backend.req.TempletPageUpdateRequest;
import com.hitrsutpay.sa.backend.res.MessageResponse;
import com.hitrsutpay.sa.backend.res.Option;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.TempletPageResponse;
import com.hitrsutpay.sa.backend.service.TpTempletPageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 頁面樣式維護
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="TempletPage(頁面樣式維護) API")
public class TpTempletPageController {
	
	public static final String TEMPLET_PAGE_GET_URL = "/get-templet-page/{type}";
	public static final String TEMPLET_PAGE_CREATE_URL = "/create-templet-page";
	public static final String TEMPLET_PAGE_UPDATE_URL = "/update-templet-page";
	
	private final TpTempletPageService  tpTemplatePageService;
	
	@GetMapping(value ="/get-country-options", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢國家列表", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseModel<List<Option>> getCountryOptions() {
		//log.info("getCountryOptions");
		var result = tpTemplatePageService.getCountryOptions();
		return new ResponseModel<List<Option>>(result);
    }
	
	@GetMapping(value ="/get-templet-options", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢樣式列表", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseModel<Map<String , List<Option>>> getAllTemplageOptions() {
		//log.info("getCountryOptions");
		var result = tpTemplatePageService.getAllTemplageOptions();
		return new ResponseModel<Map<String , List<Option>>>(result);
    }
	
	@GetMapping(value ="/get-ezp-templet-options", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢方便付頁面型式列表", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseModel<List<Option>> getAllEZPTemplate() {
		//log.info("getAllEZPTemplate");
		var result = tpTemplatePageService.getAllEZPTemplate();
		return new ResponseModel<List<Option>>(result);
    }
	
	/**
	 * 透過type(方便付或付款頁)查詢頁面樣式資料
	 * 透過page、size回傳Page型態資料
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = TEMPLET_PAGE_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢付款頁面樣式", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<Page<TempletPageResponse>> getTempletPage(
			@PathVariable TempletPageType type,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size) {
		log.info("getTempletPage");
		Pageable pageable = PageRequest.of(page, size);
		var result = tpTemplatePageService.getTempletPage(type, pageable);
		return new ResponseModel<Page<TempletPageResponse>>(result);
	}
	
	/**
	 * 透過country(ALL)、templetId(M08)、type(PAYPAGE)新增付款頁面樣式
	 * @param request
	 * @return
	 */
	@PostMapping(value = TEMPLET_PAGE_CREATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "新增付款頁面樣式", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> createTempletPage(@Valid @RequestBody TempletPageCreateRequest request) {
		log.info("createTempletPage");
		var result = tpTemplatePageService.createTempletPage(request);
		return new ResponseModel<MessageResponse>(result);
	}
	
	/**
	 * 透過country(ALL)、templetId(M08)、type(PAYPAGE)、status(啟用)修改付款頁面樣式
	 * @param request
	 * @return
	 */
	@PostMapping(value = TEMPLET_PAGE_UPDATE_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "修改付款頁面樣式", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<MessageResponse> updateTempletPage(@Valid @RequestBody TempletPageUpdateRequest request) {
		log.info("updateTempletPage");
		var result = tpTemplatePageService.updateTempletPage(request);
		return new ResponseModel<MessageResponse>(result);
	}
}
