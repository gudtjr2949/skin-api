package com.personal.skin_api.common.security.config;

import com.personal.skin_api.common.security.JwtFilter;

import com.personal.skin_api.member.repository.entity.MemberRole;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
public class WebSecurityConfig {

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

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // ✅ Preflight 요청 허용
                        .requestMatchers("/api/v1/**").hasAuthority(MemberRole.GENERAL.toString()) // ✅ /api/v1/** 는 USER 권한 필요
                        .anyRequest().permitAll() // 나머지는 모두 허용
                );

        // 권한 규칙 설정
//        http.logout((logoutConfig) -> {
//            logoutConfig.logoutUrl("/api/v1/members/logout")
//                    .addLogoutHandler((request, response, authentication) -> {
//                        HttpSession session = request.getSession();
//                        session.invalidate();
//                    });
//            logoutConfig.logoutSuccessHandler((request, response, authentication) -> {
//                response.setStatus(HttpStatus.OK.value());
//                response.getWriter().write("logout");
//                response.getWriter().flush();
//            });
//        });

        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
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