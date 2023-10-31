package com.cjsrhd94.boilerplate.global.error.exception;

import com.cjsrhd94.boilerplate.global.error.ErrorCode;

public class JwtNotValidException extends BaseException {
	public JwtNotValidException() {
		super(ErrorCode.JWT_NOT_VALID);
	}

	public JwtNotValidException(String message) {
		super(message, ErrorCode.JWT_NOT_VALID);
	}
}
