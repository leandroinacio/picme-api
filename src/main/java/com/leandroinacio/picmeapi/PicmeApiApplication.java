package com.leandroinacio.picmeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PicmeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicmeApiApplication.class, args);
	}
}
