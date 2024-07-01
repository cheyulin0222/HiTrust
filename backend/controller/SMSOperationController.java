package com.hitrsutpay.sa.backend.controller;



import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitrsutpay.sa.backend.req.SMSLogDataRequest;
import com.hitrsutpay.sa.backend.req.SmsFeeReportRequest;
import com.hitrsutpay.sa.backend.res.Option;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.SMSLogDataResponse;
import com.hitrsutpay.sa.backend.service.SMSOperationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SMS_SEND_LOG資料表
 * 查詢簡訊資料
 * 下載各收單每月簡訊費用報表
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name="SMS Operation (簡訊功能) API")
public class SMSOperationController {

	public static final String SMS_FEE_REPORT_DOWNLOAD_URL = "/download-sms-fee-report";
	public static final String SMS_LOG_DATA_GET_URL = "/get-sms-log-data";
	public static final String ACQUIRER_GET_URL = "/get-acquirer-options";
	
	
	private final SMSOperationService  smsOperationService;
	
	/**
	 * 查詢收單行列表
	 * @param request
	 * @return
	 */
	@GetMapping(value = ACQUIRER_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢收單行列表", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseModel<List<Option>> getAcquirerOptions() {
		log.info("getInstallmentIssuer");
		List<Option> result = smsOperationService.getAcquirerOption();
		return new ResponseModel<List<Option>>(result);
	}
	
	
	/**
	 * 下載各收單每月簡訊費用報表
	 * @param smsFeeReportRequest
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = SMS_FEE_REPORT_DOWNLOAD_URL, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	@Operation(summary = "簡訊費用報表(月)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Resource> exportSmsOperationFeeReport(@Valid SmsFeeReportRequest smsFeeReportRequest) throws Exception {
		log.info("exportSmsOperationFeeReport");
		// 產生檔名
		String filename =  smsFeeReportRequest.getYear()+"-"+smsFeeReportRequest.getMonth()+ "_" + smsFeeReportRequest.getAcqId() + "_SmsFee.xls";
		// 將資料庫查詢到的資料以excel的形式讀入
		InputStreamResource file = new InputStreamResource(smsOperationService.exportSmsOperationFeeReport(smsFeeReportRequest));
	    // 回傳excel檔
		return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
    }
	
	/**
	 * 依動態條件(收單行、訂單編號、商店代號、手機號碼、發送時間、簡訊類型、發送結果動)
	 * 至SMS_SEND_LOG資料表查詢簡訊資料
	 * @param request
	 * @return
	 */
	@GetMapping(value = SMS_LOG_DATA_GET_URL, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "查詢簡訊資料", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<List<SMSLogDataResponse>> getSMSLogData(@Valid SMSLogDataRequest request) {
		log.info("SMSOperationController.getSMSLogData");
		// 取得簡訊資料
		var result = smsOperationService.getSMSLogData(request);
		// 回傳結果
		return new ResponseModel<List<SMSLogDataResponse>>(result);
	}
}
