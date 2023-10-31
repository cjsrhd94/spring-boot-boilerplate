package com.cjsrhd94.boilerplate.global.exception.custom;

import com.cjsrhd94.boilerplate.global.exception.BaseException;
import com.cjsrhd94.boilerplate.global.exception.ErrorCode;

public class JwtNotValidException extends BaseException {
	public JwtNotValidException() {
		super(ErrorCode.JWT_NOT_VALID);
	}

	public JwtNotValidException(String message) {
		super(message, ErrorCode.JWT_NOT_VALID);
	}
}
