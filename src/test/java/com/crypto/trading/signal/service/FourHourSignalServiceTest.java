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
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FourHourSignalServiceTest {

    @InjectMocks
    FourHourSignalService fourHourSignalService;

    @Mock
    FourHourRepository fourHourRepository;

    @Test
    void shouldFindSellStrongSignals() {
        when(fourHourRepository.findBySellStrength(any())).thenReturn(List.of(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(1)).toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        List<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);

        assertTrue(result.stream().filter(it -> !SignalStrength.STRONG.equals(it.sellStrength()))
                .toList().isEmpty());
        verify(fourHourRepository, times(1)).findBySellStrength(any());
    }

    @Test
    void shouldFindBuyMediumSignals() {
        when(fourHourRepository.findByBuyStrength(any())).thenReturn(List.of(new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(2)).toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        List<FourHour> result = fourHourSignalService.getByStrength(TradingSignal.BUY, SignalStrength.MEDIUM);

        assertTrue(result.stream().filter(it -> !SignalStrength.MEDIUM.equals(it.buyStrength()))
                .toList().isEmpty());
        verify(fourHourRepository, times(1)).findByBuyStrength(any());
    }

    @Test
    void shouldFilterOneDayOldSignals() {
        List<FourHour> data =List.of(
                new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(25)).toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0),
                new FourHour("BTCUSDT", Instant.now().minus(Duration.ofHours(28)).toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0),
                new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)
                );

        List<FourHour> result = fourHourSignalService.filterOutdated(data);

        assertEquals(1, result.size());

    }
}
