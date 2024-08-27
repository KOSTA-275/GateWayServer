package com.dowadream.gateway.config;

import com.dowadream.gateway.JWT.JWTFilter;
import com.dowadream.gateway.JWT.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {
    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        // JWT 필터 추가
        http.addFilterAt(new JWTFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION);
        // CORS 설정 추가
        http.cors(cors -> cors
                .configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://ec2-3-35-253-143.ap-northeast-2.compute.amazonaws.com:3000"); // 허용할 출처
                    corsConfig.addAllowedMethod("*"); // 허용할 HTTP 메서드
                    corsConfig.addAllowedHeader("*"); // 허용할 헤더
                    corsConfig.setAllowCredentials(true); // 자격 증명 허용
                    return corsConfig;
                })
        );

        // 접근 권한 설정
        http.authorizeExchange(exchange ->
                exchange
                        .pathMatchers("/jwt/**").permitAll() // 인증 필요 없음
                        .pathMatchers("/board/**").hasAnyRole("ADMIN", "USER") // ADMIN 또는 USER 권한이 있는 사용자만 접근 허용
                        .pathMatchers("/customercare/inquiry_nolist").permitAll()
                        .pathMatchers("/customercare/**").hasAnyRole("ADMIN", "USER") // ADMIN 또는 USER 권한이 있는 사용자만 접근 허용
                        .pathMatchers("/user/**").hasRole("USER")
                        .anyExchange().authenticated() // 나머지 요청은 인증 필요
        );
        // 권한이 부족할 때 HTTP 403 Forbidden 응답과 한글 메시지 반환
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(accessDeniedHandler())
        );

        return http.build();
    }

    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler() {
        return (exchange, denied) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN); // 403 Forbidden 상태 코드 설정
            response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
            String message = "접근 권한이 없습니다."; // 한글 메시지 설정
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))); // 메시지와 함께 응답 완료
        };
    }
}