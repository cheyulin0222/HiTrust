package com.hitrsutpay.sa.backend.res;

import com.hitrsutpay.sa.backend.enums.Avalibility;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettingResponse {

	@Schema(description="TPID")
	private String tpId;
	
	@Schema(description="TPOPEMAIL")
	private String tpopEmail;
	
	@Schema(description="SMTPHOST")
	private String smtpHost;
	
	@Schema(description="PAYPAGE")
	private String payPage;
	
	@Schema(description="ERRPAGE")
	private String errPage;
	
	@Schema(description="TPKEY")
	private String tpKey;
	
	@Schema(description="IBMPM")
	private String ibmPm;
	
	@Schema(description="SET")
	private String set;
	
	@Schema(description="TRACE")
	private String trace;
	
	@Schema(description="LOG")
	private String log;
	
	@Schema(description="BYPASS")
	private String bypass;
	
	@Schema(description="PMHOST")
	private String pmHost;
	
	@Schema(description="PMPORT")
	private String pmPort;
	
	@Schema(description="PMID")
	private String pmId;
	
	@Schema(description="PMPWD")
	private String pmPwd;
	
	@Schema(description="PMSSL")
	private String pmSSL;
	
	@Schema(description="VISA3DPAGE")
	private String visa3DPage;
	
	@Schema(description="THREED_REDIRECT_MODE_FLAG")
	private Avalibility threedRedirectModeFlag;
	
	@Schema(description="CSP_SRI_FLAG")
	private Avalibility cspSriFlag;
}
