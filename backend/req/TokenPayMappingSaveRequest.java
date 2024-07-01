package com.hitrsutpay.sa.backend.req;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TokenPayMappingSaveRequest extends UpdateRequestBase {

	@NotBlank
	@Size(min = 1, max = 10)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	@Schema(description = "授權商代")
	private String regMerId;
	
	@Schema(description = "儲存清單")
	@NotEmpty
	@Valid
	private List<TokenPayMappingRequest> saveList;
}
