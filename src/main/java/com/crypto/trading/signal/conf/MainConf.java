package com.crypto.trading.signal.conf;

import com.tictactec.ta.lib.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class MainConf {

    @Bean
    public Core indicatorCore() {
        return new Core();
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
