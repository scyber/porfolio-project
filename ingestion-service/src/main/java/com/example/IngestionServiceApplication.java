package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "User Events Ingestion API", version = "1.0", description = "API accepts user Events"))

@SpringBootApplication
public class IngestionServiceApplication {

        public static void main(String[] args) {
                SpringApplication.run(IngestionServiceApplication.class, args);

        }

}
