package com.cjsrhd94.boilerplate.global.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	// COMMON
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
	ENTITY_NOT_FOUND(NOT_FOUND, "C002", "존재하지 않는 엔티티입니다."),
	INVALID_INPUT_VALUE(BAD_REQUEST, "C003", "요청한 값이 올바르지 않습니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C004", "해당 HTTP 메서드는 지원하지 않습니다."),

	// AUTH
	AUTHENTICATION_ERROR(BAD_REQUEST, "A001", "인증 오류가 발생하였습니다."),
	ACCESS_DENIED(FORBIDDEN, "A002", "접근이 거부되었습니다.")
	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}