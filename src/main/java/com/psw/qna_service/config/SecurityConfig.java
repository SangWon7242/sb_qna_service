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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(
            formLogin -> formLogin
                // GET
                // 시큐리티에게 우리가 만든 로그인 페이지 url을 알려준다.
                // 만약에 하지 않으면 기본 로그인 페이지 url은 /login 이다.
                .loginPage("/user/login") // 사용자 정의 로그인 페이지

                // POST
                // 시큐리티에게 로그인 폼 처리 url을 알려준다.
                .loginProcessingUrl("/user/login") // 로그인 처리 요청 경로
                .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트 경로
        )
        .logout(
            logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션을 무효화
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
