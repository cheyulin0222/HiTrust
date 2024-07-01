package com.hitrsutpay.sa.backend.req;

import com.hitrsutpay.sa.backend.enums.Avalibility;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class SystemSettingUpdateRequest extends UpdateRequestBase{
	
	@Size(min = 1, max = 10)
	@NotBlank
	@Schema(description = "TPID")
	private String tpId;
	
	@Size(min = 1, max = 50)
	@NotBlank
	@Schema(description = "TPOPEMAIL")
	private String tpopEmail;
	
	@Size(min = 1, max = 50)
	@NotBlank
	@Schema(description = "SMTPHOST")
	private String smtpHost;
	
	@Size(min = 1, max = 100)
	@NotBlank
	@Schema(description="PAYPAGE")
	private String payPage;
	
	@Size(min = 1, max = 100)
	@NotBlank
	@Schema(description="ERRPAGE")
	private String errPage;
	
	@Size(min = 1, max = 2048)
	@NotBlank
	@Schema(description="TPKEY")
	private String tpKey;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="IBMPM")
	private String ibmPm;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="SET")
	private String set;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="TRACE")
	private String trace;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="LOG")
	private String log;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="BYPASS")
	private String bypass;
	
	@Size(min = 1, max = 15)
	@NotBlank
	@Schema(description="PMHOST")
	private String pmHost;
	
	@Size(min = 1, max = 6)
	@NotBlank
	@Schema(description="PMPORT")
	private String pmPort;
	
	@Size(min = 1, max = 20)
	@NotBlank
	@Schema(description="PMID")
	private String pmId;
	
	@Size(min = 1, max = 20)
	@NotBlank
	@Schema(description="PMPWD")
	private String pmPwd;
	
	@Size(min = 1, max = 1)
	@NotBlank
	@Schema(description="PMSSL")
	private String pmSSL;
	
	@Size(min = 1, max = 100)
	@NotBlank
	@Schema(description="VISA3DPAGE")
	private String visa3DPage;
	
	@NotNull
	@Schema(description="是否開啟3D Redirect Mode")
	private Avalibility threedRedirectModeFlag;
	
	@NotNull
	@Schema(description="是否開啟CSP&SRI")
	private Avalibility cspSriFlag;
}
