package com.example.dynamicEpoch;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableRabbit
@SpringBootApplication
public class DynamicEpochApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicEpochApiApplication.class, args);
	}

}
