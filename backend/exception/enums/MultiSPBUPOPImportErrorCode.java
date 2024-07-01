package com.hitrsutpay.sa.backend.exception.enums;

public enum MultiSPBUPOPImportErrorCode implements BusinessExceptionDisplay{
	_001("匯入失敗，重複的acqMerId"),
	_002("匯入失敗，重複的acqTerminalId"),
	_003("匯入失敗，重複的mackey"),
	_004("匯入失敗，檔案讀取失敗"),
	_005("匯入失敗，未上傳文件"),
	_006("匯入失敗，上傳的文件不為CSV格式");
	
	private String message;
	
	private MultiSPBUPOPImportErrorCode (String message) {
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
