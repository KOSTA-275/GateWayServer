package com.dowadream.gateway.JWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/jwt")
public class JWTController {

    private static final Logger logger = LoggerFactory.getLogger(JWTController.class);

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login_success")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequest request) {
        logger.info("요청 데이터: {}", request);
        String token = jwtUtil.createJwt(request.getUserEmail(), request.getRole(), 60*60*60L);
        logger.info("생성된 토큰: {}", token);
        return Mono.just(ResponseEntity.ok(token));
    }

    @PostMapping("/role_auth")
    public Mono<ResponseEntity<LoginRequest>>  login(@RequestHeader("Authorization") String token) {
        // Bearer 토큰에서 실제 JWT 부분만 추출

        String jwtToken = token.substring(7);
        String role = jwtUtil.getRole(jwtToken);
        LoginRequest auth = new LoginRequest();
        auth.setRole(jwtUtil.getRole(jwtToken));
        auth.setUserEmail(jwtUtil.getUserEmail(jwtToken));
        return Mono.just(ResponseEntity.ok(auth));

    }

}
