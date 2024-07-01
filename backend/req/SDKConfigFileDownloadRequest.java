package com.hitrsutpay.sa.backend.req;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SDKConfigFileDownloadRequest {

	@Pattern(regexp = "^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Must be in the format 'yyyy/MM/dd HH:mm:ss'")
	@NotBlank
	@Schema(description = "建立時間")
	private String buildTime;
	@NotBlank
	@Schema(description = "加密商代設定資訊")
	private String payload;
}
