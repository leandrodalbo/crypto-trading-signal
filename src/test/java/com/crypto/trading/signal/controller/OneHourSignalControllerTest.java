package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(OneHourSignalController.class)
public class OneHourSignalControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    private OneHourSignalService service;

    @Test
    void shouldGetAll() throws Exception {
        given(service.getAll()).willReturn(Flux.just(new OneHour("BTCUSDT", TradingSignal.BUY, 0)));

        client.get()
                .uri("/onehour/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).getAll();
    }
}
