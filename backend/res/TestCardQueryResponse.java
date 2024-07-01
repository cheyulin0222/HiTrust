package com.hitrsutpay.sa.backend.res;

import java.math.BigDecimal;

import com.hitrsutpay.sa.backend.enums.TestCardStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCardQueryResponse {
	
	@Schema(description="信用卡號")
	private String creditCardNum;
	
	@Schema(description="效期(YYMM)")
	private String expiry;
	
	@Schema(description="CVC2")
	private String cvc2;
	
	@Schema(description="CREDIT額度")
    private BigDecimal creditLimit;
	
	@Schema(description="DEBIT額度")
    private BigDecimal debitLimit;
	
	@Schema(description="建立卡號者")
    private String updateUser;
	
	@Schema(description="使用狀態")
    private TestCardStatus status;
	
	@Schema(description="紅利點數")
    private BigDecimal redemPoint;

}
