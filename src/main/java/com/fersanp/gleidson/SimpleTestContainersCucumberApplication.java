package com.fersanp.gleidson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SimpleTestContainersCucumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleTestContainersCucumberApplication.class, args);
	}

}
