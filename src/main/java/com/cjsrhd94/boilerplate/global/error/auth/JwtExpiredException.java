package com.cjsrhd94.boilerplate.global.error.auth;

import com.cjsrhd94.boilerplate.global.error.business.BaseException;
import com.cjsrhd94.boilerplate.global.error.business.ErrorCode;

public class JwtExpiredException extends BaseException {
	public JwtExpiredException() {
		super(ErrorCode.JWT_EXPIRED);
	}

	public JwtExpiredException(String message) {
		super(message, ErrorCode.JWT_EXPIRED);
	}
}
