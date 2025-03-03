package com.personal.skin_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SkinApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SkinApiApplication.class, args);
	}
}