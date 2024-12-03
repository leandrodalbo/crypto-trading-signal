package com.crypto.trading.signal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConf {
    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                new Info()
                        .title("Crypto Trading Signals API")
                        .version("1.0.0")
        );
    }
}
