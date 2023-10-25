package com.cjsrhd94.boilerplate.member.dto;

import com.cjsrhd94.boilerplate.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class MemberDto {

	@Data
	public static class SignUp {
		@NotBlank
		private String userName;
		@NotBlank
		private String password;
		@NotBlank
		private String email;
		@NotBlank
		private String phone;
		public Member toEntity(String encodedPassword) {
			return Member.builder()
				.userName(userName)
				.password(encodedPassword)
				.email(email)
				.phone(phone)
				.build();
		}
	}
}
