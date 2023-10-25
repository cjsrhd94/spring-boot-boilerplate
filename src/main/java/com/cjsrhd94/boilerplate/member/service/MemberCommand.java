package com.cjsrhd94.boilerplate.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjsrhd94.boilerplate.global.s3.FileUtil;
import com.cjsrhd94.boilerplate.global.s3.S3Service;
import com.cjsrhd94.boilerplate.member.dto.MemberDto;
import com.cjsrhd94.boilerplate.member.entity.Member;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommand {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	private final S3Service s3Service;

	public Long signUp(MemberDto.SignUp dto) {
		return memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())))
			.getId();
	}

	public Long updateProfile(Long id, MemberDto.Profile dto) {
		Member member = memberRepository.getReferenceById(id);
		String filePath = s3Service.upload(dto.getProfile(), FileUtil.TEST_PATH);
		member.updateProfile(dto.getProfile().getOriginalFilename(), filePath);
		return member.getId();
	}
}
