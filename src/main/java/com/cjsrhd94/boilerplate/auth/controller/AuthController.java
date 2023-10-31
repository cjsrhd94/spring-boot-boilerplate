package com.cjsrhd94.boilerplate.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cjsrhd94.boilerplate.auth.service.AuthService;
import com.cjsrhd94.boilerplate.global.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("api/v1/auth/reissue")
	public ResponseEntity<Void> reissue(
		HttpServletResponse response,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestHeader("Refresh-token") String refreshToken
	) {
		authService.reissue(response, userDetails, refreshToken);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/v1/auth/logout")
	public ResponseEntity<Void> logOut(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		authService.logOut(userDetails);
		return ResponseEntity.ok().build();
	}
}