package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.FourHourSignalService;
import com.crypto.trading.signal.service.OneDaySignalService;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(SignalController.class)
public class SignalControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    private OneDaySignalService oneDaySignalService;

    @MockBean
    private OneHourSignalService oneHourSignalService;

    @MockBean
    private FourHourSignalService fourHourSignalService;

    @Test
    void shouldGetAllOneHour() {
        given(oneHourSignalService.getAll()).willReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri("/signals/onehour/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(oneHourSignalService, times(1)).getAll();
    }

    @Test
    void shouldGetAllOneDay() {
        given(oneDaySignalService.getAll()).willReturn(Flux.just(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri("/signals/oneday/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(oneDaySignalService, times(1)).getAll();
    }

    @Test
    void shouldGetAllFourHour() {
        given(fourHourSignalService.getAll()).willReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri("/signals/fourhour/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(fourHourSignalService, times(1)).getAll();
    }

    @Test
    void shouldFailWithInvalidSignalAndStrengthValues() {

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/fourhour")
                        .queryParam("signal", 1)
                        .queryParam("strength", SignalStrength.LOW)
                        .build())
                .exchange()
                .expectStatus()
                .is4xxClientError();

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/onehour")
                        .queryParam("signal", TradingSignal.BUY)
                        .queryParam("strength", "rubbish")
                        .build())
                .exchange()
                .expectStatus()
                .is4xxClientError();

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/oneday")
                        .queryParam("signal", TradingSignal.BUY)
                        .queryParam("strength", "")
                        .build())
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void shouldFindOneHourBySignalAndStrengthValues() {
        given(oneHourSignalService.getByStrength(any(), any())).willReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/onehour")
                        .queryParam("signal", TradingSignal.BUY)
                        .queryParam("strength", SignalStrength.LOW)
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(oneHourSignalService, times(1)).getByStrength(any(), any());
    }

    @Test
    void shouldFindFourHourBySignalAndStrengthValues() {
        given(fourHourSignalService.getByStrength(any(), any())).willReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/fourhour")
                        .queryParam("signal", TradingSignal.BUY)
                        .queryParam("strength", SignalStrength.LOW)
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(fourHourSignalService, times(1)).getByStrength(any(), any());
    }

    @Test
    void shouldFindOneDayBySignalAndStrengthValues() {
        given(oneDaySignalService.getByStrength(any(), any())).willReturn(Flux.just(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/signals/oneday")
                        .queryParam("signal", TradingSignal.BUY)
                        .queryParam("strength", SignalStrength.LOW)
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(oneDaySignalService, times(1)).getByStrength(any(), any());
    }

}
