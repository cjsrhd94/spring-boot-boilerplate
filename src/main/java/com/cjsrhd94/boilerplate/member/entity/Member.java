package com.cjsrhd94.boilerplate.member.entity;

import org.springframework.util.StringUtils;

import com.cjsrhd94.boilerplate.global.audit.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", unique = true)
	private String userName;
	private String password;

	private String email;
	private String phone;

	@Embedded
	private Profile profile = new Profile();

	@Builder
	public Member(
		Long id, String userName, String password,
		String email, String phone, Profile profile
	) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.profile = profile;
	}

	public void updateProfile(String fileName, String filePath) {
		if (StringUtils.hasText(fileName) && StringUtils.hasText(filePath)) {
			this.profile = new Profile(fileName, filePath);
		}
	}
}
