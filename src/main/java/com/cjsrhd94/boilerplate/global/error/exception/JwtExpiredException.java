package com.cjsrhd94.boilerplate.global.error.exception;

import com.cjsrhd94.boilerplate.global.error.ErrorCode;

public class JwtExpiredException extends BaseException {
	public JwtExpiredException() {
		super(ErrorCode.JWT_EXPIRED);
	}

	public JwtExpiredException(String message) {
		super(message, ErrorCode.JWT_EXPIRED);
	}
}
