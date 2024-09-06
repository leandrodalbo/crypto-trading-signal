package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;


@ExtendWith(MockitoExtension.class)
public class OneHourSignalServiceTest {

    @Mock
    OneHourRepository repository;

    @InjectMocks
    OneHourSignalService service;

    @Test
    void shouldFindOneHourSymbolById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(new OneHour("BTCUSDT", TradingSignal.BUY, 0)));

        Mono<OneHour> result = service.getById("BTCUSDT");

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());
        verify(repository, times(1)).findById(anyString());
    }

    @Test
    void shouldFindAllSymbols() {
        when(repository.findAll()).thenReturn(Flux.just(new OneHour("BTCUSDT", TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(repository, times(1)).findAll();
    }
}
