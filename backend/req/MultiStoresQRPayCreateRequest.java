package com.hitrsutpay.sa.backend.req;

import org.springframework.web.multipart.MultipartFile;

import com.hitrsutpay.sa.backend.enums.QrPayMerchantType;
import com.hitrsutpay.sa.backend.valid.FileCheck;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MultiStoresQRPayCreateRequest{
	
	@FileCheck(request = true,fileExt= {"csv"})
	@Schema(description="新增QR商家CSV檔")
	private MultipartFile merchantsFile;
	
	private QrPayMerchantType qrPayMerchantType;
	
}
