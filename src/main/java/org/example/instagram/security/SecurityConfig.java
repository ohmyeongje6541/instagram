package org.example.instagram.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스가 스프링 설정임을 표시
@EnableWebSecurity // springSecurity를 활성화
public class SecurityConfig {

    @Bean //Spring Security의 핵심 보안 필터 체인을 설정하는 Bean
    // HttpSecurity : 객체를 통해 HTTP보안 설정을 구성
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/auth/**", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login") // 커스텀 로그인 페이지 경로
                .loginProcessingUrl("/auth/login") // 로그인 폼 제출 시 처리할 URL
                .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 경로
                .failureUrl("/auth/login?error=true") // 로그인 실패 시 이동할 경로
                .permitAll() // 로그인 페이지는 누구나 접근 가능
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
        // permitAll(): "/", "/auth/**", "/css/**", "/js/**" 인증없이 접근 가능
        // authenticated(): 그 외 모든 요청은 인증 필요
    }

    @Bean // 비밀번호 암호화에 사용할 인코더 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
