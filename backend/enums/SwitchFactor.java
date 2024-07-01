package com.hitrsutpay.sa.backend.enums;

import java.util.Arrays;

public enum SwitchFactor {
	
	OFF("0"),
	ON("1");
	
    private String val;
	
	private SwitchFactor(String val){
		this.val = val;
	}

	public String getValue() {
		return val;
	}
	
	public static SwitchFactor get(String val) {
		return Arrays.stream(SwitchFactor.values())
				.filter(u -> u.getValue().equals(val)).findAny().orElseThrow();
		
	}
	
}

