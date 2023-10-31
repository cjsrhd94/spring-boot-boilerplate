package com.cjsrhd94.boilerplate.auth.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjsrhd94.boilerplate.global.exception.custom.JwtNotValidException;
import com.cjsrhd94.boilerplate.global.security.JwtProperties;
import com.cjsrhd94.boilerplate.global.security.JwtService;
import com.cjsrhd94.boilerplate.global.security.UserDetailsImpl;
import com.cjsrhd94.boilerplate.member.entity.Member;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@Getter
@RequiredArgsConstructor
@Transactional
public class AuthService {
	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	public void reissue(HttpServletRequest request, HttpServletResponse response) {
		String oldRefreshToken = jwtService.extractRefreshToken(request);
		Member member = memberRepository.findByRefreshToken(oldRefreshToken)
			.orElseThrow(JwtNotValidException::new);
		LocalDateTime now = LocalDateTime.now();
		// 리프레시 토큰이 유효하지 않다면 exception을 발생시킨다.
		if (!member.isValidRefreshToken(oldRefreshToken, now)) {
			throw new JwtNotValidException();
		}

		String accessToken = jwtService.createAccessToken(member.getUsername(), now);
		String refreshToken = jwtService.createRefreshToken(member.getUsername(), now);

		jwtService.saveRefreshToken(
			member.getUsername(),
			refreshToken,
			now.plus(JwtProperties.REFRESH_EXPIRATION_TIME, ChronoUnit.MILLIS)
		);

		jwtService.setAccessTokenToHeader(response, accessToken);
		jwtService.setRefreshTokenToHeader(response, refreshToken);
	}

	public void logOut(UserDetailsImpl userDetails) {
		Member member = memberRepository.getReferenceById(userDetails.getMemberId());
		member.deleteRefreshToken();
	}
}
