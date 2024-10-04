package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.FourHourRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FourHourSignalServiceTest {

    @InjectMocks
    FourHourSignalService fourHourSignalService;

    @Mock
    FourHourRepository fourHourRepository;


    @Test
    void shouldFindAllSymbols() {
        when(fourHourRepository.findAll()).thenReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<FourHour> result = fourHourSignalService.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(fourHourRepository, times(1)).findAll();
    }
}
