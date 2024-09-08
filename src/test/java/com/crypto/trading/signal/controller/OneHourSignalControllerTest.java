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
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;

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

    @Test
    void shouldGetASymbol() throws Exception {
        given(service.getById(anyString())).willReturn(Mono.empty());

        client.get()
                .uri("/onehour/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).getById(anyString());
    }

    @Test
    void shouldRefreshASymbol() throws Exception {
        doNothing().when(service).refresh(anyString());

        client.put()
                .uri("/onehour/refresh/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).refresh(anyString());
    }

    @Test
    void shouldSaveAnewSymbol() throws Exception {
        doNothing().when(service).saveNewSymbol(anyString());

        client.post()
                .uri("/onehour/add/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).saveNewSymbol(anyString());
    }

    @Test
    void shouldDeleteASymbol() throws Exception {
        doNothing().when(service).deleteSymbol(anyString());

        client.delete()
                .uri("/onehour/delete/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).deleteSymbol(anyString());
    }

}
