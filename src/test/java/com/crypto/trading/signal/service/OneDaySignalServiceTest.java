package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    void shouldFindSellStrongSignals() {
        when(oneDayRepository.findBySellStrength(any())).thenReturn(List.of(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        List<OneDay> result = service.getByStrength(TradingSignal.SELL, SignalStrength.STRONG);
        assertTrue(result.stream().filter(it -> !SignalStrength.STRONG.equals(it.sellStrength()))
                .toList().isEmpty());
        verify(oneDayRepository, times(1)).findBySellStrength(any());
    }

    @Test
    void shouldFindBuyLowSignals() {
        when(oneDayRepository.findByBuyStrength(any())).thenReturn(List.of(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        List<OneDay> result = service.getByStrength(TradingSignal.BUY, SignalStrength.LOW);

        assertTrue(result.stream().filter(it -> !SignalStrength.LOW.equals(it.buyStrength()))
                .toList().isEmpty());
        verify(oneDayRepository, times(1)).findByBuyStrength(any());
    }

}
