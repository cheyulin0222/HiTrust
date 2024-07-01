package com.hitrsutpay.sa.backend.exception.enums;

public enum StoreTransactionStatisticReportError implements BusinessExceptionDisplay{
	
	_001("日期錯誤"),
	_002("日期格式轉換錯誤");
	
	private String message;

	private StoreTransactionStatisticReportError (String message) {
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
