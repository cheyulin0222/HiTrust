package com.hitrsutpay.sa.backend.req;

import com.hitrsutpay.sa.backend.enums.TempletPageType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TempletPageRequestBase {

	
	@NotNull
	@Size(min = 0, max = 10)
	@Schema(description = "國家")
	@Pattern(regexp = "^$|^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters or be empty")
	private String country;
	
	
	@NotBlank
	@Size(min = 1, max = 10)
	@Schema(description = "樣式")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	private String templetId;
	
	@Schema(description = "頁面樣式")
	@NotNull
	private TempletPageType type;
	
	
}
