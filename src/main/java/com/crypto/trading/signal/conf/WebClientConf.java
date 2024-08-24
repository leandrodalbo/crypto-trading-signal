package com.crypto.trading.signal.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConf {

    @Bean
    WebClient webClient(WebClient.Builder builder, CryptoDataConf dataConf) {
        return builder.baseUrl(dataConf.apiEndpoint()).build();
    }
}
