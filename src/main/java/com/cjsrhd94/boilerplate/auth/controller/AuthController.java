package com.cjsrhd94.boilerplate.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjsrhd94.boilerplate.auth.service.AuthService;
import com.cjsrhd94.boilerplate.global.security.UserDetailsImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("api/v1/auth/reissue")
	public ResponseEntity<Void> reissue(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		authService.reissue(request, response);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/v1/auth/logout")
	public ResponseEntity<Void> logOut(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		authService.logOut(userDetails);
		return ResponseEntity.ok().build();
	}
}