package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class OneDaySignalServiceTest {

    @InjectMocks
    OneDaySignalService service;

    @Mock
    OneDayRepository oneDayRepository;

    @Test
    void willFindAllRecords() {
        when(oneDayRepository.findAll()).thenReturn(Flux.just(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneDay> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(oneDayRepository, times(1)).findAll();
    }
}
