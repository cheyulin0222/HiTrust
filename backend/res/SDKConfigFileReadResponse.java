package com.hitrsutpay.sa.backend.res;

import java.util.List;

import com.hitrsutpay.sa.backend.vo.MerChantConfVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SDKConfigFileReadResponse {

	@Schema(description = "商代設定資訊")
	private List<MerChantConfVO> parseAjson;
}
