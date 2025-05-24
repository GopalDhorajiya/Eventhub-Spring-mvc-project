package com.dhorajiya.gopal.EM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dhorajiya.gopal")
public class EmApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmApplication.class, args);
	}
}