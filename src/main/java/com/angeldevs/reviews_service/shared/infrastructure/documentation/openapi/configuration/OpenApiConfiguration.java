package com.angeldevs.reviews_service.shared.infrastructure.documentation.openapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {
        @Bean
        public OpenAPI learningPlatformOpenApi() {
                // General Configuration
                var openApi = new OpenAPI();
                openApi.info(new Info().title("Eventify API").version("v1.0.0"));

                return openApi;
        }
}
