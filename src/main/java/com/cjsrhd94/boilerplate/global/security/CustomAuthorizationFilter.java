package com.cjsrhd94.boilerplate.global.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cjsrhd94.boilerplate.member.entity.Member;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends BasicAuthenticationFilter {
	private final JwtService jwtService;
	private final MemberRepository memberRepository;

	public CustomAuthorizationFilter(
		AuthenticationManager authenticationManager,
		JwtService jwtService,
		MemberRepository memberRepository
	) {
		super(authenticationManager);
		this.jwtService = jwtService;
		this.memberRepository = memberRepository;
	}

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain
	) throws IOException, ServletException {
		/*
		 * 1. header에 'Authorization'이 있는지,
		 * 2. value가 'TOKEN_PREFIX'로 시작하는지 검증한다.
		 *
		 * 없으면 다시 필터에 태운다.
		 * */
		log.info("[AuthorizationFilter(BasicAuthenticationFilter)]");
		log.info("--> try to find token prefix");
		String header = request.getHeader(JwtProperties.ACCESS_HEADER_PREFIX);
		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			log.info("----> fail to find token prefix");
			return;
		}
		// 'TOKEN_PREFIX'를 제거한다.
		String accessToken = jwtService.extractAccessToken(request);

		// 유효한 JWT 토큰인지 확인한다.
		if (jwtService.validateToken(accessToken)) {
			log.info("----> complete valid access token");
			try {
				log.info("------> try save access token in security context");
				String username = jwtService.extractUsernameFrom(accessToken);
				Member member = memberRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));

				/*
				 * 1. 시큐리티 내 권한 처리를 위해 'UsernamePasswordAuthenticationToken'을 만들고,
				 * 2. 이를 통해 Authentication 객체를 만든다.
				 * */
				UserDetailsImpl userDetails =
					new UserDetailsImpl(member);
				Authentication auth =
					new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
					);

				// Authentication 객체를 시큐리티 세션에 저장한다.
				SecurityContextHolder.getContext().setAuthentication(auth);
				log.info("----> success save access token in security context!");
			} catch (UsernameNotFoundException e) {
				log.warn("----> fail to save access token in security context!");
				throw new IOException();
			}
		}

		chain.doFilter(request, response);
	}
}
