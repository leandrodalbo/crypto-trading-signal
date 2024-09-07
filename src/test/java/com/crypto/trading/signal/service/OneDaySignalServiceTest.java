package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;


@ExtendWith(MockitoExtension.class)
public class OneDaySignalServiceTest {

    @InjectMocks
    OneDaySignalService service;

    @Mock
    AdapterService adapterService;

    @Mock
    private OneDayRepository oneDayRepository;

    @Mock
    private BinanceData binanceData;

    @Mock
    private Random random;

    @Mock
    private SmaStrategy smaStrategy;

    @Test
    void willFindAllRecords() {
        when(oneDayRepository.findAll()).thenReturn(Flux.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        Flux<OneDay> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(oneDayRepository, times(1)).findAll();
    }

    @Test
    void willFindById() {
        when(oneDayRepository.findById(anyString())).thenReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        Mono<OneDay> result = service.getById("BTCUSDT");

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());
        verify(oneDayRepository, times(1)).findById(anyString());
    }

    @Test
    void randomRefresh() {
        when(adapterService.closingPrices(any())).thenReturn(new float[]{23.3f});
        when(oneDayRepository.findAll()).thenReturn(Flux.just(new OneDay("BTCUSDT", TradingSignal.SELL, 0)));
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[]{new Candle(23.3f, 23.5f, 23.1f, 23.3f)}));
        when(smaStrategy.smaSignal(any())).thenReturn(TradingSignal.BUY);
        when(oneDayRepository.save(any())).thenReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));
        when(random.nextInt(0, 1)).thenReturn(0);

        service.randomRefresh();

        verify(oneDayRepository, times(1)).findAll();
        verify(oneDayRepository, times(1)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
        verify(smaStrategy, times(1)).smaSignal(any());
        verify(adapterService, times(1)).closingPrices(any());
    }

    @Test
    void shouldNotSaveAnewSymbolIfItAlreadyExists() {
        when(oneDayRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[0]));

        service.saveNewSymbol("BTCUSDT");

        verify(oneDayRepository, times(1)).existsById(anyString());
        verify(oneDayRepository, times(0)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
    }

    @Test
    void shouldNotSaveAnewSymbolIfItNotExistsInBinance() {
        when(oneDayRepository.existsById(anyString())).thenReturn(Mono.just(false));
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[0]));

        service.saveNewSymbol("BTCUSDT");

        verify(oneDayRepository, times(1)).existsById(anyString());
        verify(oneDayRepository, times(0)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
    }

    @Test
    void shouldSaveAnewSymbol() {
        when(oneDayRepository.existsById(anyString())).thenReturn(Mono.just(false));
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[]{new Candle(2f, 3f, 3f, 4f)}));
        when(oneDayRepository.save(any())).thenReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.NONE, 0)));
        service.saveNewSymbol("BTCUSDT");

        verify(oneDayRepository, times(1)).existsById(anyString());
        verify(oneDayRepository, times(1)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
    }

    @Test
    void shouldNotDeleteASymbolIfItDoesNotExists() {
        when(oneDayRepository.existsById(anyString())).thenReturn(Mono.just(false));

        service.deleteSymbol("BTCUSDT");

        verify(oneDayRepository, times(1)).existsById(anyString());
        verify(oneDayRepository, times(0)).deleteById(anyString());
    }

    @Test
    void shouldDeleteASymbol() {
        when(oneDayRepository.existsById(anyString())).thenReturn(Mono.just(true));
        when(oneDayRepository.deleteById(anyString())).thenReturn(Mono.empty());

        service.deleteSymbol("BTCUSDT");

        verify(oneDayRepository, times(1)).existsById(anyString());
        verify(oneDayRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldNotRefreshASymbolIfItDoesNotExists() {
        when(oneDayRepository.findById(anyString())).thenReturn(Mono.empty());

        service.refresh("BTCUSDT");

        verify(oneDayRepository, times(1)).findById(anyString());
        verify(oneDayRepository, times(0)).save(any());
    }

    @Test
    void shouldRefreshASymbol() {
        when(adapterService.closingPrices(any())).thenReturn(new float[]{23.3f});
        when(oneDayRepository.findById(anyString())).thenReturn(Mono.just(new OneDay("BTCUSDT", TradingSignal.SELL, 0)));
        when(oneDayRepository.save(any())).thenReturn(Mono.empty());
        when(binanceData.fetchOHLC(anyString(), any())).thenReturn(Mono.just(new Candle[]{new Candle(23.3f, 23.5f, 23.1f, 23.3f)}));

        service.refresh("BTCUSDT");

        verify(oneDayRepository, times(1)).findById(anyString());
        verify(oneDayRepository, times(1)).save(any());
        verify(binanceData, times(1)).fetchOHLC(anyString(), any());
        verify(adapterService, times(1)).closingPrices(any());
    }
}
