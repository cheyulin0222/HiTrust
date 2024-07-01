package com.hitrsutpay.sa.backend.controller;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hitrsutpay.sa.backend.exception.SaApiException;
import com.hitrsutpay.sa.backend.exception.enums.IOSConfigErrorCode;
import com.hitrsutpay.sa.backend.req.SDKConfigFileEncryptRequest;
import com.hitrsutpay.sa.backend.req.SDKConfigFileTestRequest;
import com.hitrsutpay.sa.backend.req.SDKConfigFileDownloadRequest;
import com.hitrsutpay.sa.backend.res.ResponseModel;
import com.hitrsutpay.sa.backend.res.SDKConfigFileReadResponse;
import com.hitrsutpay.sa.backend.res.SDKConfigFileEncryptResponse;
import com.hitrsutpay.sa.backend.service.SDKConfigFileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * SDKConfigFileService
 * 讀取檔案回傳檔案內容
 * 修改、新增IOS Config資料加密並回傳
 * 下載加密後檔案
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "SDKConfigFile(IOS Config檔設定) API")
public class SDKConfigFileController {

	public static final String SDK_CONFIG_FILE_READ_URL = "/read-sdk-config-file";
	public static final String SDK_CONFIG_FILE_ENCRYPT_URL = "/encrypt-sdk-config-file";
	public static final String SDK_CONFIG_FILE_DOWNLOAD_URL = "/download-sdk-config-file";
	public static final String SDK_CONFIG_FILE_TEST_URL = "/test-sdk-config-file";
	
	private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
	
	private final SDKConfigFileService iosConfigService;
	

	
	/**
	 * 讀取檔案
	 * 檔案檢查
	 * 將檔案轉成java物件回傳
	 * @param file
	 * @param version
	 * @return
	 */
	@PostMapping(value = SDK_CONFIG_FILE_READ_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@Operation(summary = "讀取IOSConfig", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<SDKConfigFileReadResponse> readSDKConfigFile(
			@RequestPart("file") MultipartFile file){
		log.info("IOSConfigController.readSDKConfigFile");
		
		// 是否上傳檔案
		if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
			log.error("未上傳文件");
			throw new SaApiException(IOSConfigErrorCode._001);
		}
		// 檔案大小是否小於 10 MB
	    if (file.getSize() > MAX_FILE_SIZE) {
	    	log.error("檔案大小超過10MB, FILE SIZE={}", file.getSize());
			throw new SaApiException(IOSConfigErrorCode._002);
	    }
	    // 檔案格式是否為json
	    String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	    if (!"json".equalsIgnoreCase(fileExtension) && !MediaType.APPLICATION_JSON_VALUE.equals(file.getContentType())) {
	    	log.error("上傳的文件不為json格式, File.Extension={}, File.ContentType={}", fileExtension, file.getContentType());
	    	throw new SaApiException(IOSConfigErrorCode._003);
	    }
	    // 讀取檔案
	    var result = iosConfigService.readSDKConfigFile(file);
	    // 回傳結果
	    return new ResponseModel<SDKConfigFileReadResponse>(result);
	}
	
	/**
	 * 將IOS Config資料加密
	 * @param request
	 * @return
	 */
	@PostMapping(value = SDK_CONFIG_FILE_ENCRYPT_URL, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "加密IOSConfig", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseModel<SDKConfigFileEncryptResponse> encryptSDKConfigFile(@Valid @RequestBody SDKConfigFileEncryptRequest sdkConfigFileConvertRequest) {
		log.info("IOSConfigController.convertSDKConfigFile");
		
		// 將request轉為特定格式並加密
		var result = iosConfigService.encryptSDKConfigFile(sdkConfigFileConvertRequest);
		// 回傳結果
		return new ResponseModel<SDKConfigFileEncryptResponse>(result);
	}
	
	/**
	 * 下載加密後的IOS Config json檔
	 * @param request
	 * @return
	 */
	@PostMapping(value = SDK_CONFIG_FILE_DOWNLOAD_URL,  produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	@Operation(summary = "下載IOSConfig", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Resource> downloadSDKConfigFile(@Valid @RequestBody SDKConfigFileDownloadRequest request) throws Exception {
		log.info("IOSConfigController.downloadSDKConfigFile");

		String filename = "ToolKitConf.json";
		// 將request轉成bytes
		byte[] bytesData = iosConfigService.convertToBytes(request);
		// 將bytes資料轉成回傳格式
		var result = new ByteArrayResource(bytesData);
        // 回傳結果
        return ResponseEntity.ok()
        		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        		.contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);
	}
	
	/**
	 * 測試用
	 * @param request
	 * @return
	 */
	@PostMapping(value = SDK_CONFIG_FILE_TEST_URL)
	@Operation(summary = "下載IOSConfig(測試用)", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Resource> downloadTestSDKConfigFile(@RequestBody SDKConfigFileTestRequest request) {
		log.info("IOSConfigController.testSDKConfigFile");

		// 將request轉成bytes
		byte[] bytesData = iosConfigService.createTestFile(request.getTestList());
		// 將bytes資料轉成回傳格式
		var result = new ByteArrayResource(bytesData);
		
		// 回傳檔案設定
		HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(bytesData.length);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "ToolKitConfTest.json");
		
        // 回傳結果
        return ResponseEntity.ok()
                .headers(headers)
                .body(result);
	}
}
