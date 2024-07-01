package com.hitrsutpay.sa.backend.exception.enums;

public enum ImportMultiSPBStoreUPOPErrorCode implements BusinessExceptionDisplay {
	
	_001("未上傳文件"),
	_002("上傳的文件不為csv格式"),
	_003("匯入失敗，商代皆不存在"),
	_004("匯入失敗，檔案讀取失敗");

	private String message;
	
	private ImportMultiSPBStoreUPOPErrorCode (String message) {
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
