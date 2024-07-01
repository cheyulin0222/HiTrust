package com.hitrsutpay.sa.backend.res;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.hitrsutpay.sa.backend.constant.MerchantConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SMSLogDataResponse {
	@Schema(description = "商家代碼")
	private String merId;
	
	@Schema(description = "訂單編號")
	private String orderNum;
	
	@Schema(description = "收單行")
	private String acqId;

	@Schema(description = "簡訊內容")
	private String content64;
	
	@Schema(description = "簡訊類型")
	private String type;
	
	@Schema(description = "手機號碼")
	private String telMask;
	
	@Schema(description = "發送結果")
	private String result;
	
	@Schema(description = "發送時間", pattern = "yyyy-MM-dd HH:mm:ss", type="string",example = "2024-03-28 00:00:00")
	private Timestamp sendingTime;
	
	@Schema(description = "備註")
	private String remark;

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getAcqId() {
		return acqId;
	}

	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}

	public String getContent64() {
		return content64;
	}

	public void setContent64(String content64) {
		this.content64 = content64;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTelMask() {
		return telMask;
	}

	public void setTelMask(String telMask) {
		this.telMask = telMask;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSendingTime() {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return (sendingTime == null) ? MerchantConstant.NA : dateFormat.format(sendingTime);
	}

	public void setSendingTime(Timestamp sendingTime) {
		this.sendingTime = sendingTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
