package com.example.bit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.bit")
public class BitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitApplication.class, args);
	}

}
