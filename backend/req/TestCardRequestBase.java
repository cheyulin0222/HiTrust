package com.hitrsutpay.sa.backend.req;

import java.math.BigDecimal;

import com.hitrsutpay.sa.backend.enums.TestCardStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TestCardRequestBase {
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{15,16}$", message = "Must contain 15 ~ 16 digits")
	@Schema(description = "信用卡號")
	private String creditCardNum;

	@NotBlank
	@Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])$", message = "Expiry must be 4 characters long, and Expiry month must be between 01 and 12")
	@Schema(description = "效期(YYMM)")
	private String expiry;

	@NotNull
	@Schema(description = "CREDIT額度")
	private BigDecimal creditLimit;

	@NotNull
	@Schema(description = "DEBIT額度")
	private BigDecimal debitLimit;

	@NotNull
	@Schema(description = "使用狀態")
	private TestCardStatus status;

	@NotNull
	@Schema(description = "紅利點數")
	private BigDecimal redemPoint;

	@NotBlank
	@Pattern(regexp = "^\\d{3}$", message = "CVC2 must contain 3 digits")	
	@Schema(description = "CVC2")
	private String cvc2;
}
