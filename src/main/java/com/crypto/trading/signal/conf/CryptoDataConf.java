package com.crypto.trading.signal.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "crypto-data")
public record CryptoDataConf(String apiEndpoint, List<String> symbols) {
}