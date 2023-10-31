package com.cjsrhd94.boilerplate.global.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String code;
	private String message;
	private List<FieldError> errors;

	private ErrorResponse(ErrorCode code, List<FieldError> errors) {
		this.code = code.getCode();
		this.message = code.getMessage();
		this.errors = errors;
	}

	private ErrorResponse(ErrorCode code) {
		this.code = code.getCode();
		this.message = code.getMessage();
		this.errors = new ArrayList<>();
	}

	public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
		return new ErrorResponse(code, FieldError.of(bindingResult));
	}

	public static ErrorResponse of(ErrorCode code) {
		return new ErrorResponse(code);
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
		String value = Optional.ofNullable(e.getValue())
			.map(Object::toString)
			.orElse("");

		List<ErrorResponse.FieldError> errors 
			= ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
		return new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errors);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {
		private String field;
		private String value;
		private String message;

		private FieldError(String field, String value, String message) {
			this.field = field;
			this.value = value;
			this.message = message;
		}

		public static List<FieldError> of(String field, String value, String message) {
			return new ArrayList<>(List.of(new FieldError(field, value, message)));
		}

		private static List<FieldError> of(BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
				.map(
					e -> new FieldError(
						e.getField(),
						Objects.requireNonNull(e.getRejectedValue()).toString(),
						e.getDefaultMessage()
					)
				)
				.collect(Collectors.toList());
		}
	}
}
