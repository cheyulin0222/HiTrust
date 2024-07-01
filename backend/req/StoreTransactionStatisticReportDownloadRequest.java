package com.hitrsutpay.sa.backend.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StoreTransactionStatisticReportDownloadRequest {
	
	@NotNull
	@Schema(description="起始時間",example = "2024-01-01",type = "string",format = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@NotNull
	@Schema(description="結束時間",example = "2024-01-31",type = "string",format = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	
}
