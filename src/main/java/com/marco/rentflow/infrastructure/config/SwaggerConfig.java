package com.marco.rentflow.infrastructure.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // Clave de seguridad: Solo arranca si el .env dice dev
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RentFlow API")
                        .version("1.0")
                        .description("Documentación interactiva de los servicios REST de RentFlow."));
    }
}
