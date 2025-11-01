package com.angeldevs.reviews_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ReviewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsServiceApplication.class, args);
	}

}
