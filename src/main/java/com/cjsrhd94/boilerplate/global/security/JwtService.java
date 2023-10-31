package com.cjsrhd94.boilerplate.global.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cjsrhd94.boilerplate.global.exception.custom.JwtExpiredException;
import com.cjsrhd94.boilerplate.global.exception.custom.JwtNotValidException;
import com.cjsrhd94.boilerplate.member.repository.MemberQuery;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
	private final MemberQuery memberQuery;

	@Transactional
	public void saveRefreshToken(String username, String refreshToken, LocalDateTime expireAt) {
		memberQuery.findMemberByUsername(username)
			.saveRefreshToken(refreshToken, expireAt);
	}

	public String createAccessToken(String username, LocalDateTime now) {
		return JWT.create()
			.withIssuer(JwtProperties.ISSUER)
			.withSubject(JwtProperties.ACCESS_TOKEN)
			.withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
			.withExpiresAt(
				Date.from(now.plus(JwtProperties.ACCESS_EXPIRATION_TIME, ChronoUnit.MILLIS)
					.atZone(ZoneId.systemDefault()).toInstant()))
			.withClaim(JwtProperties.USERNAME, username)
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}

	public String createRefreshToken(String username, LocalDateTime now) {
		return JWT.create()
			.withIssuer(JwtProperties.ISSUER)
			.withSubject(JwtProperties.REFRESH_TOKEN)
			.withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
			.withExpiresAt(
				Date.from(now.plus(JwtProperties.REFRESH_EXPIRATION_TIME, ChronoUnit.MILLIS)
					.atZone(ZoneId.systemDefault()).toInstant()))
			.withClaim(JwtProperties.USERNAME, username)
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}

	public void setAccessTokenToHeader(HttpServletResponse response, String accessToken) {
		response.addHeader(JwtProperties.ACCESS_HEADER_PREFIX, JwtProperties.TOKEN_PREFIX + accessToken);
	}

	public void setRefreshTokenToHeader(HttpServletResponse response, String refreshToken) {
		response.addHeader(JwtProperties.REFRESH_HEADER_PREFIX, JwtProperties.TOKEN_PREFIX + refreshToken);
	}

	public void setResponseMessage(
		HttpServletResponse response,
		Boolean result,
		String message
	) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		JSONObject json = new JSONObject();
		json
			.put("success", result)
			.put("message", message);
		response.getWriter().print(json);
	}

	public String extractAccessToken(HttpServletRequest request) {
		return request.getHeader(JwtProperties.ACCESS_HEADER_PREFIX)
			.replace(JwtProperties.TOKEN_PREFIX, "");
	}

	public String extractRefreshToken(HttpServletRequest request) {
		return request.getHeader(JwtProperties.REFRESH_HEADER_PREFIX)
			.replace(JwtProperties.TOKEN_PREFIX, "");
	}

	public boolean validateToken(String accessToken) {
		try {
			JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
				.build().verify(accessToken);
			return true;
		} catch (TokenExpiredException e) {
			throw new JwtExpiredException();
		} catch (SignatureVerificationException | JWTDecodeException e) {
			throw new JwtNotValidException();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String extractUsernameFrom(String accessToken) {
		return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
			.build()
			.verify(accessToken)
			.getClaim(JwtProperties.USERNAME)
			.asString();
	}
}
