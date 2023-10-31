package com.cjsrhd94.boilerplate.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthReqDto {
	@Data
	public static class Login {
		@NotBlank
		private String username;
		@NotBlank
		private String password;
	}
}
