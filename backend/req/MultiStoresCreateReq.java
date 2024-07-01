package com.hitrsutpay.sa.backend.req;

import org.springframework.web.multipart.MultipartFile;

import com.hitrsutpay.sa.backend.valid.FileCheck;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MultiStoresCreateReq{
	
	@FileCheck(request = true,fileExt= {"csv"})
	@Schema(description="批次新增商家CSV檔")
	private MultipartFile merchantsFile;
	
}
