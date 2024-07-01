package com.hitrsutpay.sa.backend.req;

import com.hitrsutpay.sa.backend.enums.TokenPayStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TokenPayMappingRequest {

	@NotBlank
	@Size(min = 1, max = 10)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	@Schema(description = "被授權商代")
	private String authMerId;
	
	@NotNull
	@Schema(description = "授權狀態")
	private TokenPayStatus authStatus;
}
