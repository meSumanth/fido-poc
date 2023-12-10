package com.fido.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class FidoPocApp {

	public static void main(String[] args) {
		SpringApplication.run(FidoPocApp.class, args);
	}
	
	@GetMapping("/health")
	public String health() {
		return "FIDO service Up and Running!";
	}

}
