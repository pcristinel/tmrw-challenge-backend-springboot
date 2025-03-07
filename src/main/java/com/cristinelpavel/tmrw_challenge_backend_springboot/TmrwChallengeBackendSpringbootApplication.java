package com.cristinelpavel.tmrw_challenge_backend_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TmrwChallengeBackendSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmrwChallengeBackendSpringbootApplication.class, args);
	}
}
