package com.psw.qna_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    return http.build();
  }

  @Bean
  // 비밀번호 암호화 검증
  // 인증 시 암호화된 비밀번호와 비교
  // BCrypt는 암호화 알고리즘으로 비밀번호 보안성을 향상 시킴
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
