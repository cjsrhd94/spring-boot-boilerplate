package com.cjsrhd94.boilerplate.member.entity;

import org.hibernate.envers.Audited;
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
@Audited
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String username;
	private String password;

	private String email;
	private String phone;

	@Embedded
	private Profile profile = new Profile();

	@Builder
	public Member(
		Long id, String username, String password,
		String email, String phone, Profile profile
	) {
		this.id = id;
		this.username = username;
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
