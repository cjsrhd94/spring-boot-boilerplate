package com.cjsrhd94.boilerplate.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.cjsrhd94.boilerplate.member.repository.MemberRepository;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig{
	private final UserDetailsService userDetailService;
	private final JwtService jwtService;
	private final MemberRepository memberRepository;
	private final HandlerExceptionResolver handlerExceptionResolver;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(
				authorize -> {
					authorize.requestMatchers("/**").permitAll();
					authorize.anyRequest().authenticated();
				}
			)
			.addFilter(customUsernamePasswordAuthenticationFilter())
			.addFilterBefore(
				new CustomAuthorizationFilter(authenticationManager(), jwtService, memberRepository),
				BasicAuthenticationFilter.class
			)
			.addFilterBefore(
				new JwtAuthorizationExceptionFilter(handlerExceptionResolver),
				CustomAuthorizationFilter.class
			)
			.exceptionHandling(
				authenticationManager -> authenticationManager
					.authenticationEntryPoint(
						new CustomAuthenticationEntryPoint(handlerExceptionResolver)
					)
					.accessDeniedHandler(
						new CustomAccessDeniedHandler(handlerExceptionResolver)
					)
			)
			.build();
	}

	public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {
		CustomUsernamePasswordAuthenticationFilter filter
			= new CustomUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService, handlerExceptionResolver);
		filter.setFilterProcessesUrl("/api/v1/login");
		return filter;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/api/v1/auth/**");
	}
}