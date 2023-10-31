package com.cjsrhd94.boilerplate.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cjsrhd94.boilerplate.global.exception.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * http status 500
     * 시스템 예외시 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * http status 500
     * 객체가 메서드 호출을 처리하기에 적절치 않을 때 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        log.error(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * http status 405
     * 지원하지 않는 HTTP 메서드 호출시 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e
    ) {
        log.warn(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * http status 400
     * 타입 바인딩에 실패하였을 때 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e
    ) {
        log.warn(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 400
     * 유효성 검사를 통과하지 못했을 때 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 400
     * 스프링 시큐리티 인증 과정을 통과하지 못했을 떄 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        log.warn(e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(ErrorCode.AUTHENTICATION_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 403
     * 접근에 필요한 권한이 없을 때 동작
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.warn(e.getMessage(), e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * http status 400
     * 자격 증명에 실패했을 때 동작
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleMemberNotValid(BadCredentialsException e) {
        log.warn(e.getMessage(), e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.MEMBER_NOT_VALID);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 400
     * Token이 만료되었을 때 동작
     */
    @ExceptionHandler(TokenExpiredException.class)
    protected ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
        log.warn(e.getMessage(), e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.JWT_EXPIRED);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 400
     * Token이 유효하지 않을 때 동작
     */
    @ExceptionHandler(value = {JWTDecodeException.class, SignatureVerificationException.class})
    protected ResponseEntity<ErrorResponse> handleJwtNotValidException(Exception e) {
        log.warn(e.getMessage(), e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.JWT_NOT_VALID);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * http status 200
     * 비즈니스 로직 문제시 동작
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus().value()));
    }
}
