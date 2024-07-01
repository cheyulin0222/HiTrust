package com.hitrsutpay.sa.backend.enums;

import java.util.Arrays;

public enum TestCardStatus {
    
	OPEN("O"),
	CLOSED("C");
    
    private String val;
    
    private TestCardStatus(String val) {
        this.val = val;
    }
    
    public String getValue() {
        return val;
    }

    public static TestCardStatus get(String val) {
		return Arrays.stream(TestCardStatus.values())
			   .filter(u -> u.getValue().equals(val)).findAny().orElseThrow();
		
	}
}
