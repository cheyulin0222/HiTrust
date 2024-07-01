package com.hitrsutpay.sa.backend.enums;

import java.util.Arrays;

public enum SMSType {

	TRANS("01", "交易"),
	CREATE("02", "新建"),
	UPDATE("03", "更新"),
	UNKNOWN("UNKNOWN", "未定義的狀態"),
	NA("NA" , "N/A");
	
	private String value;
	private String label;
	
	private SMSType (String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static SMSType get(String val) {
		return (val == null) ? NA : Arrays.stream(SMSType.values())
				.filter(u -> u.getValue().equals(val))
				.findAny()
				.orElse(UNKNOWN);
	}
}
