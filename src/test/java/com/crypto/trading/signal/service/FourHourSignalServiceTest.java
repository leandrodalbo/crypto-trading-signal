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

import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FourHourSignalServiceTest {

    @InjectMocks
    FourHourSignalService fourHourSignalService;

    @Mock
    FourHourRepository fourHourRepository;


    @Test
    void shouldFindSellStrongSignals() {
        when(fourHourRepository.findBySellStrength(any())).thenReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(1)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> SignalStrength.STRONG.equals(it.sellStrength()))
                .verifyComplete();

        verify(fourHourRepository, times(1)).findBySellStrength(any());
    }

    @Test
    void shouldFindBuyMediumSignals() {
        when(fourHourRepository.findByBuyStrength(any())).thenReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(2)).toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.BUY, SignalStrength.MEDIUM);

        StepVerifier.create(result)
                .thenConsumeWhile(it -> SignalStrength.MEDIUM.equals(it.buyStrength()))
                .verifyComplete();

        verify(fourHourRepository, times(1)).findByBuyStrength(any());
    }

    @Test
    void shouldFilterOldBuySignals() {
        when(fourHourRepository.findByBuyStrength(any())).thenReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(25)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.BUY, SignalStrength.LOW);

        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();

        verify(fourHourRepository, times(1)).findByBuyStrength(any());
    }

    @Test
    void shouldFilterOldSellSignals() {
        when(fourHourRepository.findBySellStrength(any())).thenReturn(Flux.just(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(25)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        Flux<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);

        StepVerifier.create(result)
                .expectSubscription()
                .verifyComplete();

        verify(fourHourRepository, times(1)).findBySellStrength(any());
    }
}
