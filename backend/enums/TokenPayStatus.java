package com.hitrsutpay.sa.backend.enums;

public enum TokenPayStatus {

	CANCEL("L"),
	AUTH("A");
	
	private String value;
	
	private TokenPayStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public static TokenPayStatus getTokenPayStatusByValue(String value) {
		for (TokenPayStatus status : TokenPayStatus.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid Status value: " + value);
	}

}
