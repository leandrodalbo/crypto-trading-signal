package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.OneDaySignalService;
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

@WebFluxTest(OneDaySignalController.class)
public class OneDaySignalControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    private OneDaySignalService service;

    @Test
    void shouldGetAll() throws Exception {
        given(service.getAll()).willReturn(Flux.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        client.get()
                .uri("/oneday/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).getAll();
    }

    @Test
    void shouldSaveAnewSymbol() throws Exception {
        given(service.saveNewSymbol(anyString())).willReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.NONE, 0)));

        client.post()
                .uri("/oneday/add/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).saveNewSymbol(anyString());
    }

    @Test
    void shouldDeleteASymbol() throws Exception {
        given(service.deleteSymbol(anyString())).willReturn(Mono.empty());

        client.delete()
                .uri("/oneday/delete/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).deleteSymbol(anyString());
    }

    @Test
    void shouldGetASymbol() throws Exception {
        given(service.getById(anyString())).willReturn(Mono.empty());

        client.get()
                .uri("/oneday/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).getById(anyString());
    }

    @Test
    void shouldRefreshASymbol() throws Exception {
        doNothing().when(service).refresh(anyString());

        client.put()
                .uri("/oneday/refresh/BTCUSDT")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(service, times(1)).refresh(anyString());
    }
}
