package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OneDaySignalServiceTest {

    @InjectMocks
    OneDaySignalService service;
    @Mock
    private OneDayRepository repository;

    @Mock
    private BinanceData binanceData;

    @Mock
    private SmaStrategy smaStrategy;

    @Test
    void willFindAllRecords() {

        when(repository.findAll()).thenReturn(Flux.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        Flux<OneDay> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(repository, times(1)).findAll();

    }

    @Test
    void randomRefresh() {

        when(repository.findAll()).thenReturn(Flux.just(new OneDay("BTCUSDT", TradingSignal.SELL, 0)));
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[]{new Candle(23.3f, 23.5f, 23.1f, 23.3f)}));
        when(smaStrategy.smaSignal(any())).thenReturn(TradingSignal.BUY);
        when(repository.save(any())).thenReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        service.refresh();


        verify(repository, times(1)).findAll();
        verify(repository, times(1)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
        verify(smaStrategy, times(1)).smaSignal(any());

    }
}
