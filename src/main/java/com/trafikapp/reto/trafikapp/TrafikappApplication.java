package com.trafikapp.reto.trafikapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrafikappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrafikappApplication.class, args);
	}

}
