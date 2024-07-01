package com.hitrsutpay.sa.backend.exception.enums;

public enum TestCardErrorCode implements BusinessExceptionDisplay{
	
	_001("無此測試卡號"),
	_002("修改失敗，此測試卡號不存在"),
	_003("新增失敗，此測試卡號已存在"),
	_004("效期驗證失敗，效期必須大於現在日期"),
	_005("卡號驗證失敗，必須為15~16位數字");

	private String message;

	private TestCardErrorCode (String message) {
		this.message = message;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public String description() {
		return this.getClass().getSimpleName()+this.name();
	}	

}
