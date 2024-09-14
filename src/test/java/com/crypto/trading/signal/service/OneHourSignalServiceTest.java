package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.errorhandler.exeptions.SymbolAlreadyExistsException;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyString;


@ExtendWith(MockitoExtension.class)
public class OneHourSignalServiceTest {

    @InjectMocks
    OneHourSignalService service;

    @Mock
    OneHourRepository oneHourRepository;

    @Mock
    AdapterService adapterService;

    @Mock
    private BinanceData binanceData;

    @Mock
    private Random random;

    @Mock
    private SmaStrategy smaStrategy;


    @Test
    void shouldFindAllSymbols() {
        when(oneHourRepository.findAll()).thenReturn(Flux.just(new OneHour("BTCUSDT", TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(oneHourRepository, times(1)).findAll();
    }

    @Test
    void shouldRefreshASymbol() throws InvalidSymbolException {
        when(adapterService.closingPrices(any())).thenReturn(new float[]{23.3f});
        when(oneHourRepository.findById(anyString())).thenReturn(Mono.just(new OneHour("BTCUSDT", TradingSignal.SELL, 0)));
        when(oneHourRepository.save(any())).thenReturn(Mono.empty());
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[]{new Candle(23.3f, 23.5f, 23.1f, 23.3f, 232)}));

        service.refresh("BTCUSDT");

        verify(oneHourRepository, times(1)).findById(anyString());
        verify(oneHourRepository, times(1)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
        verify(adapterService, times(1)).closingPrices(any());
    }

    @Test
    void shouldNotRefreshASymbolIfItDoesNotExists() throws InvalidSymbolException {
        when(oneHourRepository.findById(anyString())).thenReturn(Mono.empty());

        Assertions.assertThatExceptionOfType(InvalidSymbolException.class)
                .isThrownBy(() -> service.refresh("BTCUSDT"));

        verify(oneHourRepository, times(1)).findById(anyString());
        verify(oneHourRepository, times(0)).save(any());
    }


}
