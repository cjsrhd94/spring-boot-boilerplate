package com.cjsrhd94.boilerplate.global.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.cjsrhd94.boilerplate.auth.dto.AuthDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager manager;
	private final JwtProvider jwtProvider;
	private final HandlerExceptionResolver resolver;

	public CustomUsernamePasswordAuthenticationFilter(
		AuthenticationManager authenticationManager,
		JwtProvider jwtProvider,
		HandlerExceptionResolver handlerExceptionResolver
	) {
		this.manager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.resolver = handlerExceptionResolver;
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		try {
			log.info("[UsernamePasswordAuthenticationFilter]");
			log.info("--> attempt authentication");
			// request에서 username과 password를 파싱해서 Object로 받는다.
			AuthDto.Login dto = new ObjectMapper()
				.readValue(request.getInputStream(), AuthDto.Login.class);
			/*
			 * AuthenticationManager는 사용자 아이디 / 비밀번호가 유효한 인증인지 확인한다.
			 * .authenticate()메서드 내의 Authentication이 유효한지 확인하고, Authentication 객체를 리턴한다.
			 * */
			Authentication authentication = new UsernamePasswordAuthenticationToken(
				dto.getUsername(),
				dto.getPassword()
			);

			return manager.authenticate(authentication);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult
	) throws IOException {
		UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();

		String accessToken = jwtProvider.createAccessToken(userDetails.getUsername());
		String refreshToken = jwtProvider.createRefreshToken(userDetails.getUsername());

		jwtProvider.setAccessTokenToHeader(response, accessToken);
		jwtProvider.setRefreshTokenToHeader(response, refreshToken);

		jwtProvider.setResponseMessage(response, true, "로그인 성공");
		log.info("--> success authentication");
	}

	@Override
	public void unsuccessfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authenticationException
	) {
		resolver.resolveException(request, response, null, authenticationException);
		log.warn("--> fail authentication");
	}
}
