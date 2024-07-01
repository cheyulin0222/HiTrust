package com.hitrsutpay.sa.backend.res;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hitrsutpay.sa.backend.enums.TempletPageType;
import com.hitrsutpay.sa.backend.enums.TwoFactor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TempletPageResponse {

	@Schema(description = "國家")
	private String country;
	
	@Schema(description = "樣式")
	private String templetId;
	
	@Schema(description = "頁面樣式")
	private TempletPageType type;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Schema(description = "異動時間", pattern = "yyyy-MM-dd HH:mm:ss", type="string",example = "2024-03-28 00:00:00")
	private Timestamp updateTime;
	
	@Schema(description = "狀態")
	private TwoFactor status;
	
	
}
