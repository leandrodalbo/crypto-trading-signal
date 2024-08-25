package com.crypto.trading.signal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class TradingSignal {

	public static void main(String[] args) {
		SpringApplication.run(TradingSignal.class, args);
	}

}
