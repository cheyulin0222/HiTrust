package com.hitrsutpay.sa.backend.exception.enums;

public enum SMSOperationErrorCode implements BusinessExceptionDisplay {
	
	_001("參數驗證失敗，所有參數不得為null"),
	_002("參數驗證失敗，商店代號字串長度超過10"),
	_003("參數驗證失敗，訂單編號字串長度超過20"),
	_004("參數驗證失敗，手機號碼字串長度超過32"),
	_005("參數驗證失敗，商店代號只能輸入英數字或空字串"),
	_006("參數驗證失敗，訂單編號只能輸入英數字或空字串"),
	_007("參數驗證失敗，手機號碼只能輸入英數字或空字串"),
	_008("參數驗證失敗，發送時間(起)輸入格式為yyyy-MM-dd HH:mm:ss或空字串"),
	_009("參數驗證失敗，發送時間(迄)輸入格式為yyyy-MM-dd HH:mm:ss或空字串");
	
	private String message;
	
	private SMSOperationErrorCode (String message) {
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
