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
public class MessageResponse{

	@Builder.Default
	@Schema(description = "新增是否成功")
	private boolean success = true;
	
	@Schema(description = "新增結果訊息")
	private String message;

}
