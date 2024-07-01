package com.hitrsutpay.sa.backend.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StatusCodeMappingCreateRequest {
	
	@NotBlank
	@Schema(description = "狀態碼")
	private String statusCode;
	
	@NotBlank
	@Schema(description = "狀態碼描述")
	private String statusDesc;
}
