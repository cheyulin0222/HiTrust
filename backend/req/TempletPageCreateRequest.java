package com.hitrsutpay.sa.backend.req;

import com.hitrsutpay.sa.backend.enums.TempletPageType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TempletPageCreateRequest {

	@Schema(description = "頁面樣式")
	@NotNull
	private TempletPageType type;
	
	@Schema(description = "檔案名稱")
	@NotBlank
	private String fileName;
}
