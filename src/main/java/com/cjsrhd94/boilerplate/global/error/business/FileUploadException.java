package com.cjsrhd94.boilerplate.global.error.business;

public class FileUploadException extends BaseException{
	public FileUploadException() {
		super(ErrorCode.FILE_UPLOAD_FAIL);
	}

	public FileUploadException(String message) {
		super(message, ErrorCode.FILE_UPLOAD_FAIL);
	}
}