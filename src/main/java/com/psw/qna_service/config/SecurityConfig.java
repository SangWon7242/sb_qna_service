package com.psw.qna_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http/*
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(new AntPathRequestMatcher("/question/list")).permitAll() / /question/list는 인증 없이 접근 가능
                )
                */
        .formLogin(
            formLogin -> formLogin
                .loginPage("/user/login") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/user/login") // 로그인 처리 요청 경로
                .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트 경로
        );

    return http.build();
  }

  @Bean
  // 비밀번호 암호화 검증
  // 인증 시 암호화된 비밀번호와 비교
  // BCrypt는 암호화 알고리즘으로 비밀번호 보안성을 향상 시킴
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  // 스프링 시큐리티의 인증을 처리
  // 커스텀 인증 로직을 구현할 때 필요
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
