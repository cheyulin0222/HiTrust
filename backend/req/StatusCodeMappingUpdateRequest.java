package com.hitrsutpay.sa.backend.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StatusCodeMappingUpdateRequest extends UpdateRequestBase{

	@Min(0)@Max(9999)
	@NotNull
	@Schema(description = "狀態碼ID")
	private Integer statusId;
	
	@NotBlank
	@Schema(description = "狀態碼")
	private String statusCode;
	
	@NotBlank
	@Schema(description = "狀態碼描述")
	private String statusDesc;
}
