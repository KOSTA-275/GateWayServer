//package com.dowadream.gateway.JWT;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextImpl;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//import javax.servlet.http.Cookie;
//
//public class JWTFilter implements WebFilter {
//
//    private final JWTUtil jwtUtil;
//
//    public JWTFilter(JWTUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        if (exchange.getRequest().getURI().getPath().startsWith("/jwt/")) {
//            return chain.filter(exchange);
//        }
//
//        // 요청에서 쿠키를 가져옵니다
//        String authorization = null;
//        Cookie[] cookies = exchange.getRequest().getCookies().get("Authorization");
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                authorization = cookie.getValue();
//                break; // "Authorization" 쿠키가 하나만 있다고 가정
//            }
//        }
//
//        // Authorization 쿠키가 없으면 필터 체인을 계속 진행합니다
//        if (authorization == null) {
//            return chain.filter(exchange);
//        }
//
//        // 토큰 유효성 검증
//        if (jwtUtil.isExpired(authorization)) {
//            return chain.filter(exchange); // 토큰이 만료된 경우 필터 체인을 계속 진행합니다
//        }
//
//        // 토큰에서 사용자 정보를 추출합니다
//        String username = jwtUtil.getUsername(authorization);
//        String role = jwtUtil.getRole(authorization);
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setRole(role);
//
//        // 추출한 사용자 정보를 사용하여 CustomOAuth2User 객체를 생성합니다
//        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);
//
//        // 인증 토큰을 생성합니다
//        Authentication authToken = new UsernamePasswordAuthenticationToken(
//                customOAuth2User,
//                null,
//                customOAuth2User.getAuthorities()
//        );
//
//        // 보안 컨텍스트를 설정합니다
//        SecurityContext securityContext = new SecurityContextImpl(authToken);
//        return chain.filter(exchange)
//                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
//    }
//}
