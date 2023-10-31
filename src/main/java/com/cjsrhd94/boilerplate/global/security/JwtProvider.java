package com.cjsrhd94.boilerplate.global.security;

import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cjsrhd94.boilerplate.global.error.auth.JwtExpiredException;
import com.cjsrhd94.boilerplate.global.error.auth.JwtNotValidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtProvider {
	public String createAccessToken(String username) {
		long now = System.currentTimeMillis();
		return JWT.create()
			.withIssuer(JwtProperties.ISSUER)
			.withSubject(JwtProperties.ACCESS_TOKEN)
			.withIssuedAt(new Date(now))
			.withExpiresAt(new Date(now + JwtProperties.ACCESS_EXPIRATION_TIME))
			.withClaim(JwtProperties.USERNAME, username)
			.sign(Algorithm.HMAC512(JwtProperties.SECRET));
	}

	public String createRefreshToken(String username) {
		long now = System.currentTimeMillis();
		return JWT.create()
			.withIssuer(JwtProperties.ISSUER)
			.withSubject(JwtProperties.REFRESH_TOKEN)
			.withIssuedAt(new Date(now))
			.withExpiresAt(new Date(now + JwtProperties.REFRESH_EXPIRATION_TIME))
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

	public String extractToken(HttpServletRequest request) {
		return request.getHeader(JwtProperties.ACCESS_HEADER_PREFIX)
			.replace(JwtProperties.TOKEN_PREFIX, "");
	}

	public boolean validateAccessToken(String accessToken) {
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

	public String getUsernameFrom(String accessToken) {
		return JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
			.build()
			.verify(accessToken)
			.getClaim(JwtProperties.USERNAME)
			.asString();
	}
}
