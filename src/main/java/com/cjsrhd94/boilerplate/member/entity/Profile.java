package com.cjsrhd94.boilerplate.member.entity;

import org.springframework.util.StringUtils;

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

	public void update(String fileName, String filePath) {
		if (StringUtils.hasText(fileName) && StringUtils.hasText(filePath)) {
			this.originalFileName = fileName;
			this.filePath = filePath;
		}
	}
}
