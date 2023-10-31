package com.cjsrhd94.boilerplate.global.exception.custom;

import com.cjsrhd94.boilerplate.global.exception.BaseException;
import com.cjsrhd94.boilerplate.global.exception.ErrorCode;

public class EntityNotFoundException extends BaseException {
	public EntityNotFoundException() {
		super(ErrorCode.ENTITY_NOT_FOUND);
	}

	public EntityNotFoundException(String message) {
		super(message, ErrorCode.ENTITY_NOT_FOUND);
	}
}
