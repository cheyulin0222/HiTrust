package com.hitrsutpay.sa.backend.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCodeMappingResponse {

	@Schema(description="狀態碼ID")
	private Integer statusId;
	
	@Schema(description="狀態碼")
	private String statusCode;
	
	@Schema(description="狀態碼描述")
	private String statusDesc;
}
