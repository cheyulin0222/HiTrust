package com.hitrsutpay.sa.backend.req;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TestCardUpdateRequest extends UpdateRequestBase{
	
	@NotNull
	@Valid
	@Schema(description = "測試卡號資料")
	private TestCardRequestBase testCardInfo;
	
}
