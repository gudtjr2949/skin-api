package com.personal.skin_api.common.security.config;

import com.personal.skin_api.common.security.JwtFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())); // ✅ CORS 설정 추가

        http.exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler));

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(new JwtFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // ✅ CORS 정책 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 허용할 프론트엔드 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "WS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cookie")); // 허용할 헤더
        configuration.setAllowCredentials(true); // ✅ 쿠키 허용 (HttpOnly 토큰 사용 시 필요)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 CORS 적용

        return source;
    }
}