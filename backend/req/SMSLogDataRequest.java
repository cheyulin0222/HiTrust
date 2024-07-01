package com.hitrsutpay.sa.backend.req;




import com.hitrsutpay.sa.backend.enums.AcqBank;
import com.hitrsutpay.sa.backend.enums.SMSResult;
import com.hitrsutpay.sa.backend.enums.SMSType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SMSLogDataRequest{


	@Schema(description = "收單行")
	private AcqBank acqId;

	@Size(min = 0, max = 20)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	@Schema(description = "訂單編號")
	private String orderNum;

	@Size(min = 0, max = 10)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	@Schema(description = "商店代號")
	private String merId;

	@Size(min = 0, max = 32)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Must contain only alphanumeric characters")
	@Schema(description = "手機號碼")
	private String telHash;

	@NotEmpty
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Must be in the format 'yyyy-MM-dd HH:mm:ss'")
	@Schema(description = "發送時間(起)")
	private String startTime;
	
	@NotEmpty
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "Must be in the format 'yyyy-MM-dd HH:mm:ss'")
	@Schema(description = "發送時間(迄)")
	private String endTime;
	
	@Schema(description = "簡訊類型")
	private SMSType smsType;

	@Schema(description = "發送結果")
	private SMSResult result;
}
