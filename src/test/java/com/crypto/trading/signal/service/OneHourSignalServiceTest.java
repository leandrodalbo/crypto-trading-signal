package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OneHourSignalServiceTest {

    @InjectMocks
    OneHourSignalService service;

    @Mock
    OneHourRepository oneHourRepository;

    @Test
    void shouldFindAllSymbols() {
        when(oneHourRepository.findAll()).thenReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.STRONG, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty())
                .verifyComplete();

        verify(oneHourRepository, times(1)).findAll();
    }

    @Test
    void shouldFindSellStrongSignals() {
        when(oneHourRepository.findBySellStrength(any())).thenReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> SignalStrength.STRONG.equals(it.sellStrength()))
                .verifyComplete();

        verify(oneHourRepository, times(1)).findBySellStrength(any());
    }

    @Test
    void shouldFindBuyLowSignals() {
        when(oneHourRepository.findByBuyStrength(any())).thenReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getByStrength(TradingSignal.BUY, SignalStrength.LOW);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> SignalStrength.LOW.equals(it.buyStrength()))
                .verifyComplete();

        verify(oneHourRepository, times(1)).findByBuyStrength(any());
    }

    @Test
    void shouldFilterOldBuySignals() {
        when(oneHourRepository.findByBuyStrength(any())).thenReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().minus(Duration.ofHours(3)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getByStrength(TradingSignal.BUY, SignalStrength.LOW);

        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();

        verify(oneHourRepository, times(1)).findByBuyStrength(any());
    }

    @Test
    void shouldFilterOldSellSignals() {
        when(oneHourRepository.findBySellStrength(any())).thenReturn(Flux.just(new OneHour("BTCUSDT", Instant.now().minus(Duration.ofHours(2)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);

        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();

        verify(oneHourRepository, times(1)).findBySellStrength(any());
    }
}
