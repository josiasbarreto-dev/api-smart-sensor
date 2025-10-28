package io.github.api_smart_sensor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("api-smart-sensor")
                        .version("1.0.0")
                        .description("API Smart Sensor é uma API RESTful em Java com Spring Boot para ingestão e gestão de leituras de sensores IoT. Oferece endpoints para receber dados de sensores, consultar leituras no MongoDB, deletar leituras por id e listar todas as leituras. Preparada para validação básica, persistência em MongoDB e execução em Docker.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Josias Barreto")
                                .url("https://github.com/josiasbarreto-dev")
                                .email("sr.josiasbarreto@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
