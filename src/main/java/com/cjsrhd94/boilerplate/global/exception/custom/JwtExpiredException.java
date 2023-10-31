package com.cjsrhd94.boilerplate.global.exception.custom;

import com.cjsrhd94.boilerplate.global.exception.BaseException;
import com.cjsrhd94.boilerplate.global.exception.ErrorCode;

public class JwtExpiredException extends BaseException {
	public JwtExpiredException() {
		super(ErrorCode.JWT_EXPIRED);
	}

	public JwtExpiredException(String message) {
		super(message, ErrorCode.JWT_EXPIRED);
	}
}
