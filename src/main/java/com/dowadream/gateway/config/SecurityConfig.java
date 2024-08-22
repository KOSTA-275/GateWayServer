package com.dowadream.gateway.config;

//import com.dowadream.gateway.JWT.JWTFilter;
import com.dowadream.gateway.JWT.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

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
       // http.addFilterAt(new JWTFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION);

        // 접근 권한 설정
        http.authorizeExchange(exchange ->
                exchange
                        .pathMatchers("/jwt/**").permitAll() // 인증 필요 없음
                        .pathMatchers("/board/**").permitAll() // 인증 필요 없음
                        .anyExchange().authenticated() // 나머지 요청은 인증 필요
        );

        return http.build();
    }
}
