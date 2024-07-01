package com.hitrsutpay.sa.backend.exception.enums;

public enum StatusCodeMapingErrorCode implements BusinessExceptionDisplay {

	_001("新增失敗，重複的狀態碼"), 
	_002("修改失敗，ID不存在"),
	_003("刪除失敗，ID不存在"),
	_004("修改失敗，重複的狀態碼"),
	_005("查詢失敗，請提供狀態碼");
	
	private String message;
	
	private StatusCodeMapingErrorCode (String message) {
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
