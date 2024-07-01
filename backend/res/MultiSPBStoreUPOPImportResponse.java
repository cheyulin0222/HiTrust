package com.hitrsutpay.sa.backend.res;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultiSPBStoreUPOPImportResponse {

	@Schema(description = "匯入成功清單")
	private List<String> successList;
	
	@Schema(description = "匯入失敗清單")
	private List<String> failedList;
}
