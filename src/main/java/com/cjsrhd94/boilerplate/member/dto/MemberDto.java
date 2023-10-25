package com.cjsrhd94.boilerplate.member.dto;

import com.cjsrhd94.boilerplate.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

	@Data
	public static class Detail {
		private String userName;
		private String email;
		private String phone;

		public Detail(Member member) {
			this.userName = member.getUserName();
			this.email = member.getEmail();
			this.phone = member.getPhone();
		}
	}
}
