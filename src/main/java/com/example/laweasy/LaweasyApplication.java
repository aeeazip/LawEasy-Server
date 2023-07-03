package com.example.laweasy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LaweasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaweasyApplication.class, args);
	}

}
