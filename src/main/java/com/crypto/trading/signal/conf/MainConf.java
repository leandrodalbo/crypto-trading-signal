package com.crypto.trading.signal.conf;

import com.tictactec.ta.lib.Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConf {

    @Bean
    public Core indicatorCore() {
        return new Core();
    }
}
