package com.hitrsutpay.sa.backend.req;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestCardCreateRequest {

	@Valid
	@NotNull
	@Schema(description = "測試卡號資料")
	private TestCardRequestBase testCardInfo;
}
