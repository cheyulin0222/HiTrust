package com.hitrsutpay.sa.backend.req;

import java.util.List;

import com.hitrsutpay.sa.backend.vo.MerChantConfVO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SDKConfigFileEncryptRequest {

	@NotEmpty
	@Valid
	@Schema(description = "IOS商代Config列表")
	private List<MerChantConfVO> parseAjson;
	
}
