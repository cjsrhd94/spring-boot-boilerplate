package com.cjsrhd94.boilerplate.member.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Profile {
	private String originalFileName;
	private String filePath;

	@Builder
	public Profile(String originalFileName, String filePath) {
		this.originalFileName = originalFileName;
		this.filePath = filePath;
	}
}
