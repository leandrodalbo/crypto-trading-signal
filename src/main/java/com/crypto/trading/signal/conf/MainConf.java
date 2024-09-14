package com.crypto.trading.signal.conf;

import com.tictactec.ta.lib.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;

@Configuration
public class MainConf {

    @Bean
    WebClient webClient(WebClient.Builder builder, CryptoDataConf dataConf) {
        return builder.baseUrl(dataConf.apiEndpoint()).build();
    }

    @Bean
    public Core indicatorCore() {
        return new Core();
    }
}
