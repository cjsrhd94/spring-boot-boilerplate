package com.cjsrhd94.boilerplate.global.error.exception;

import com.cjsrhd94.boilerplate.global.error.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private final ErrorCode errorCode;

	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BaseException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
