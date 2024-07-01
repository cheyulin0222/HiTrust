package com.hitrsutpay.sa.backend.req;


import com.hitrsutpay.sa.backend.enums.TwoFactor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TempletPageUpdateRequest extends UpdateRequestBase{

	
	@NotNull
	@Valid
	@Schema(description = "付款頁樣式ID")
	private TempletPageRequestBase id;
	
	@NotNull
	@Schema(description = "狀態")
	private TwoFactor status;
}
