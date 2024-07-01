package com.hitrsutpay.sa.backend.exception.enums;

public enum TempletPageErrorCode implements BusinessExceptionDisplay {

	_001("新增失敗，方便付的國家欄位必須為空字串"),
	_002("新增失敗，此付款頁樣式已存在"),
	_003("修改失敗，此付款頁樣式不存在"),
	_004("新增失敗，檔案格式錯誤");
	
	private String message;
	
	private TempletPageErrorCode (String message) {
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
