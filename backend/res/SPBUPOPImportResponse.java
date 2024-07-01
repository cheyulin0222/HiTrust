package com.hitrsutpay.sa.backend.res;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SPBUPOPImportResponse{

	private int index;
	
	private String merId;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{15}$", message = "必須為15位數字")
	private String acqMerId;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{8}$", message = "必須為8位數字")
	private String acqTerminalId;
	
	@NotBlank
	@Pattern(regexp = "^[a-z0-9]{32}$", message = "必須為32位英數字")
	private String mackey;
	
	@Builder.Default
	private boolean success = true;
	
	@Builder.Default
	private String message = "[匯入成功]";
}
