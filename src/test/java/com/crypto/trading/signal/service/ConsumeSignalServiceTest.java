package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.FourHourRepository;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsumeSignalServiceTest {
    @Mock
    private OneHourRepository oneHourRepository;

    @Mock
    private OneDayRepository oneDayRepository;

    @Mock
    private FourHourRepository fourHourRepository;

    @InjectMocks
    private ConsumeSignalService service;

    @Test
    public void willSaveOneDaySignal() {
        Signal signal = new Signal("BTCUSDT", Timeframe.D1, SignalStrength.STRONG, SignalStrength.MEDIUM, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);
        when(oneDayRepository.findById(anyString())).thenReturn(Mono.just(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));
        when(oneDayRepository.save(any())).thenReturn(Mono.empty());

        service.consumeSignal(signal);

        verify(oneDayRepository, times(1)).findById(anyString());
        verify(oneDayRepository, times(1)).save(any());
    }

    @Test
    public void willSaveOneHourSignal() {
        Signal signal = new Signal("BTCUSDT", Timeframe.H1, SignalStrength.LOW, SignalStrength.MEDIUM, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);
        when(oneHourRepository.findById(anyString())).thenReturn(Mono.just(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));
        when(oneHourRepository.save(any())).thenReturn(Mono.empty());

        service.consumeSignal(signal);

        verify(oneHourRepository, times(1)).findById(anyString());
        verify(oneHourRepository, times(1)).save(any());
    }

    @Test
    public void willSaveFourHourSignal() {
        Signal signal = new Signal("BTCUSDT", Timeframe.H4, SignalStrength.MEDIUM, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);
        when(fourHourRepository.findById(anyString())).thenReturn(Mono.just(new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.MEDIUM, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));
        when(fourHourRepository.save(any())).thenReturn(Mono.empty());

        service.consumeSignal(signal);

        verify(fourHourRepository, times(1)).findById(anyString());
        verify(fourHourRepository, times(1)).save(any());
    }

}
