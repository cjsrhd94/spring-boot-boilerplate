package com.cjsrhd94.boilerplate.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cjsrhd94.boilerplate.member.entity.Member;
import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("[UserDetailService]");
		log.info("-->load User By Username");
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(()-> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
		return new UserDetailsImpl(member);
	}
}
