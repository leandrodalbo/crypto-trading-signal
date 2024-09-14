package com.crypto.trading.signal.service.adapter;

import com.crypto.trading.signal.model.Candle;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

public class AdapterServiceTest {

    private final AdapterService adapterService = new AdapterService();

    @Test
    public void willReturnAnArrayWithTheClosingPrices() {
        assertThat(adapterService.closingPrices(Mono.just(new Candle[]{new Candle(23.3f, 23.5f, 23.1f, 23.3f, 232)})))
                .isEqualTo(new float[]{23.3f});
    }
}
