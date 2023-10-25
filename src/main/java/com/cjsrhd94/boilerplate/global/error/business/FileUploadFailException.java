package com.cjsrhd94.boilerplate.global.error.business;

public class FileUploadFailException extends BaseException{
	public FileUploadFailException() {
		super(ErrorCode.FILE_UPLOAD_FAIL);
	}

	public FileUploadFailException(String message) {
		super(message, ErrorCode.FILE_UPLOAD_FAIL);
	}
}
