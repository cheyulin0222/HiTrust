package com.hitrsutpay.sa.backend.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.MultiSPBUPOPImportErrorCode;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.SPBUPOPImportResponse;
import com.hitrsutpay.sa.backend.service.MultiSPBUPOPImportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 讀取CSV檔，檢核資料，批次新增SPBUPOP商店
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Multi SPBUPOP Stores Import(整批開通永豐銀聯功能) API")
public class MultiSPBUPOPImportController {

	public static final String MULTI_SPB_STORE_UPOP_IMPORT_URL = "/import-multi-spb-stores-upop";
	
	private final MultiSPBUPOPImportService multiSPBStoreUPOPService;
	
	/**
	 * 讀取CSV檔，檢核資料，批次新增SPBUPOP商店
	 * @param file
	 * @return
	 */
	@PostMapping(value = MULTI_SPB_STORE_UPOP_IMPORT_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@Operation(summary = "整批匯入永豐銀聯資料", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<List<SPBUPOPImportResponse>> importMultiSPBStoreUPOP(@RequestPart("file") MultipartFile file) {
		log.info("MultiSPBStoreUPOPController.importMultiSPBStoreUPOP");
		
		// 是否上傳檔案
		if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
			log.error("未上傳文件");
			throw new SaApiException(MultiSPBUPOPImportErrorCode._005);
		}
		// 檔案格式是否為csv
	    String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	    if (!"csv".equalsIgnoreCase(fileExtension) && !MediaType.TEXT_PLAIN_VALUE.equals(file.getContentType())) {
	    	log.error("上傳的文件不為 CSV 格式, File.Extension={}, File.ContentType={}", fileExtension, file.getContentType());
	    	throw new SaApiException(MultiSPBUPOPImportErrorCode._006);
	    }
		// 讀取檔案，匯入資料庫，回傳結果
		var result = multiSPBStoreUPOPService.importMultiSPBStoreUPOP(file);
		
		return new ResponseModel<List<SPBUPOPImportResponse>>(result);
	}
}
