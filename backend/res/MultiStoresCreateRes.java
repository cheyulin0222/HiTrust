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
public class MultiStoresCreateRes {

	@Schema(description = "商家代碼")
	private String merId;

	@Schema(description = "回覆訊息")
	private String message;

	@Schema(description = "處理結果")
	private boolean success;
	
	@JsonIgnore
	@Schema(description = "幣別")
	private String currency;
	
	//需將檔案放至FTP上
	@JsonIgnore
	@Schema(description = "新建外貿CSV檔")
	private String ftpCsvData;
	

	

//	@Schema(description = "商家金鑰(DER)")
//	private String merKey_DER;
//
//	@Schema(description = "商家金鑰(PEM)")
//	private String merKey_PEM;

}
