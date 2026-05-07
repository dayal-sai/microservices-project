package com.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.FeignException;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerConfigCustomizer productServiceCustomizer() {
        return CircuitBreakerConfigCustomizer
            .of("productService", builder -> builder.ignoreExceptions(FeignException.NotFound.class));
    }
}
