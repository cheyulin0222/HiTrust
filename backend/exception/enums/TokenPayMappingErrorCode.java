package com.hitrsutpay.sa.backend.exception.enums;

public enum TokenPayMappingErrorCode implements BusinessExceptionDisplay {

	_001("儲存失敗，授權商代和被授權商代重複");
	
	private String message;
	
	private TokenPayMappingErrorCode(String message) {
		this.message = message;
	}
	

	@Override
	public String message() {
		return this.message;
	}

	@Override
	public String description() {
		return this.getClass().getSimpleName() + this.name();
	}
}
