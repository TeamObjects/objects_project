package com.objects.marketbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MarketbridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketbridgeApplication.class, args);
	}

}