package com.example.arcana;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableAsync
@RestController
public class ArcanaApplication {
	@GetMapping("/")
	public String home() {
		return "finallyyyyyyyyy";
	}
	public static void main(String[] args) {
		SpringApplication.run(ArcanaApplication.class, args);
	}

}