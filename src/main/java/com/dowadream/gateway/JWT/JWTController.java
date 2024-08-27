package com.dowadream.gateway.JWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jwt")
public class JWTController {

    private static final Logger logger = LoggerFactory.getLogger(JWTController.class);

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login_success")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        logger.info("요청 데이터: {}", request);
        String token = jwtUtil.createJwt(request.getUserName(), request.getRole(), 60*60*60L);
        logger.info("생성된 토큰: {}", token);
        return Mono.just(ResponseEntity.ok(token));
    }
    @GetMapping("/test")
    public Mono<String> login() {
        return Mono.just("응"); // "응" 문자열을 반환합니다.
    }
}
