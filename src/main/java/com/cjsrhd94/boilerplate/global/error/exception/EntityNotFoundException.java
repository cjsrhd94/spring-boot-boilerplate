package com.cjsrhd94.boilerplate.global.error.exception;

import com.cjsrhd94.boilerplate.global.error.ErrorCode;

public class EntityNotFoundException extends BaseException {
	public EntityNotFoundException() {
		super(ErrorCode.ENTITY_NOT_FOUND);
	}

	public EntityNotFoundException(String message) {
		super(message, ErrorCode.ENTITY_NOT_FOUND);
	}
}
