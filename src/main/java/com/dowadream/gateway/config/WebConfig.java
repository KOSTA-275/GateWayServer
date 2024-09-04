package com.dowadream.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://ec2-3-35-253-143.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedOrigins("http://ec2-15-164-115-147.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedOrigins("http://ec2-3-39-66-166.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedOrigins("http://ec2-43-200-98-34.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}