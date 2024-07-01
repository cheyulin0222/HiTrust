package com.hitrsutpay.sa.backend.exception.enums;

public enum SystemSettingErrorCode implements BusinessExceptionDisplay {
	
	_001("操作失敗，無此TPID");
	
	private String message;
	
	private SystemSettingErrorCode (String message) {
		this.message = message;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public String description() {
		return this.getClass().getSimpleName() + this.name();
	}

}
