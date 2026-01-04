package com.example.questifysharedapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuestifysharedapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestifysharedapiApplication.class, args);
	}

}
