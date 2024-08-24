package com.crypto.trading.signal.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crypto-data")
public record CryptoDataConf(String apiEndpoint) {
}