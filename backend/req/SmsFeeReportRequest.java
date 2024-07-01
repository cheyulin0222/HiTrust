package com.hitrsutpay.sa.backend.req;

import com.hitrsutpay.sa.backend.enums.AcqBank;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SmsFeeReportRequest {
	
	@NotNull
	@Schema(description="銀行代碼")
	private AcqBank acqId;
	
	@NotNull
	@Min(2000)@Max(2100)
	@Schema(description="年")
	private int year;
	
	@NotNull
	@Min(1)@Max(12)
	@Schema(description="月")
	private int month;
	
	@NotNull
	@Positive
	@Schema(description="簡訊費用(封)")
	private double fee;

}
