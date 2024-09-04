package com.dowadream.gateway.JWT;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Order(1) // 필터 순서 설정
public class AddEmailFilter implements WebFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public AddEmailFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 요청 경로 확인
        String path = exchange.getRequest().getURI().getPath();
        if ("/users/userSelectEmail".equals(path)) {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getUserEmail(token);

                    // 사용자 정보를 요청에 추가
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Email", email)
                            .build();

                    // 수정된 요청으로 체인 진행
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                } else {
                    // JWT 검증 실패 시 401 Unauthorized 반환
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            } else {
                // Authorization 헤더가 없거나 Bearer가 아닌 경우
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // 필터를 적용하지 않을 경우 원래 요청으로 체인 진행
        return chain.filter(exchange);
    }
}
