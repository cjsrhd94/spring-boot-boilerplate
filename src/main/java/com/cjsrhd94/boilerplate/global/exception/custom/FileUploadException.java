package com.cjsrhd94.boilerplate.global.exception.custom;

import com.cjsrhd94.boilerplate.global.exception.BaseException;
import com.cjsrhd94.boilerplate.global.exception.ErrorCode;

public class FileUploadException extends BaseException {
	public FileUploadException() {
		super(ErrorCode.FILE_UPLOAD_FAIL);
	}

	public FileUploadException(String message) {
		super(message, ErrorCode.FILE_UPLOAD_FAIL);
	}
}
