package com.dowadream.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class CustomRoute {

    private static final Logger logger = LoggerFactory.getLogger(CustomRoute.class);

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customercare", r -> r.path("/customercare/**")
                        .and()
                        .method(HttpMethod.POST,HttpMethod.GET,HttpMethod.PUT,HttpMethod.DELETE)
                        .uri("lb://CUSTOMERCARE"))
                .route("errandservice", r -> r.path("/api/**")
                        .and()
                        .method(HttpMethod.POST,HttpMethod.GET,HttpMethod.PUT,HttpMethod.DELETE)
                        .uri("lb://ERRANDSERVICE"))
                .build();
    }
}