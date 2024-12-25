package com.example.wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class WalletServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}
}
