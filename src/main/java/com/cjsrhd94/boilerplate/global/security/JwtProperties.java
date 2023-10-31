package com.cjsrhd94.boilerplate.global.security;

public interface JwtProperties {
	String SECRET = "secret";
	String ISSUER = "anonymous";
	String USERNAME = "username";
	Long ACCESS_EXPIRATION_TIME = 1000*60*30L; // 30 min
	Long REFRESH_EXPIRATION_TIME = 1000*60*60*24*14L; // 2 weeks
	String TOKEN_PREFIX = "Bearer ";
	String ACCESS_HEADER_PREFIX = "Authorization";
	String REFRESH_HEADER_PREFIX = "Refresh-Token";
	String ACCESS_TOKEN = "access";
	String REFRESH_TOKEN = "refresh";
}
