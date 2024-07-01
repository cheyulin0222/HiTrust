package com.hitrsutpay.sa.backend.res;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SDKConfigFileEncryptResponse {

	@Schema(description = "SDKConfig版本(固定值)")
	private String version;
	@Schema(description = "建立時間")
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp buildTime;
	@Schema(description = "加密商代設定資訊")
	private String payload;
}
