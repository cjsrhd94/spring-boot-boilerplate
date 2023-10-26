package com.cjsrhd94.boilerplate.member.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cjsrhd94.boilerplate.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class MemberDto {

	@Data
	public static class SignUp {
		@NotBlank
		private String username;
		@NotBlank
		private String password;
		@NotBlank
		private String email;
		@NotBlank
		private String phone;
		public Member toEntity(String encodedPassword) {
			return Member.builder()
				.username(username)
				.password(encodedPassword)
				.email(email)
				.phone(phone)
				.build();
		}
	}

	@Data
	public static class Detail {
		private String username;
		private String email;
		private String phone;

		public Detail(Member member) {
			this.username = member.getUsername();
			this.email = member.getEmail();
			this.phone = member.getPhone();
		}
	}

	@Data
	public static class Profile {
		private MultipartFile profile;
	}
}
