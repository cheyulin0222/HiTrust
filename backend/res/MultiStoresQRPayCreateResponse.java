package com.hitrsutpay.sa.backend.res;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiStoresQRPayCreateResponse {

	@Schema(description = "商家代碼")
	private String merId;

	@Schema(description = "回覆訊息")
	private String message;

	@Schema(description = "處理結果")
	private boolean success;
	
	//產生設定檔使用
	@JsonIgnore
	@Schema(description = "幣別")
	private String currency;

	//註冊ApplePay使用
	@JsonIgnore
	@Schema(description = "商家名稱")
	private String merName;
	
	//註冊ApplePay使用
	@JsonIgnore
	@Schema(description = "商家網址")
	private String merURL;

}
