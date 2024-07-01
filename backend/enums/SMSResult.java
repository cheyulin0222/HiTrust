package com.hitrsutpay.sa.backend.enums;

import java.util.Arrays;

public enum SMSResult {

	SUCCESS("00", "成功"),
	FAIL99("99", "失敗(99)"),
	FAIL98("98", "失敗(98)"),
	UNKNOWN("UNKNOWN", "未定義的狀態"),
	NA("NA" , "N/A");
	
	private String value;
	private String label;
	
	private SMSResult (String value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	public String getLabel() {
		return label;
	}
	
	public static SMSResult get(String val) {
		return (val == null) ? NA : Arrays.stream(SMSResult.values())
										.filter(u -> u.getValue().equals(val))
										.findAny()
										.orElse(UNKNOWN);
	}
}
