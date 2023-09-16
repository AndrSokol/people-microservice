package org.people.user.config;

import feign.Request;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignBuilderCustomizer customizer() {
        return builder ->
                builder
                        .options(new Request.Options(Duration.ofMinutes(1), Duration.ofMinutes(1), true))
                        .logLevel(feign.Logger.Level.FULL)
                        .logger(new Slf4jLogger())
                        .retryer(new feign.Retryer.Default(1000, 8000, 3))
                        .contract(new SpringMvcContract())
                        .build();
    }
}
