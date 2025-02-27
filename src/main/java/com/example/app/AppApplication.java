package com.example.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}


	@Bean
	public OpenAPI customApi(){
		return new OpenAPI().info(new Info()
				.title("Api info")
				.version("0.0.7")
				.description("Muestra informacion de la Api pruebaTecniva 4"));

	}

}
