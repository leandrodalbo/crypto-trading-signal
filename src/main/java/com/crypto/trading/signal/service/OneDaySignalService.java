package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.message.Message;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class OneDaySignalService {

    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final OneDayRepository oneDayRepository;
    private final Random random;
    private final AdapterService adapterService;

    public OneDaySignalService(BinanceData binanceData, SmaStrategy smaStrategy, OneDayRepository oneDayRepository, Random random, AdapterService adapterService) {
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.oneDayRepository = oneDayRepository;
        this.random = random;
        this.adapterService = adapterService;
    }

    public Flux<OneDay> getAll() {
        return this.oneDayRepository.findAll();
    }

    public Mono<OneDay> getById(String symbol) {
        return this.oneDayRepository.findById(symbol);
    }

    public Mono<Object> saveNewSymbol(String symbol) {
        boolean symbolExists = Boolean.TRUE.equals(oneDayRepository.existsById(symbol).block());
        Candle[] candles = binanceData.fetchOHLC(symbol, Timeframe.D1).block();

        if (symbolExists)
            return Mono.just(Message.SYMBOL_ALREADY_EXISTS.getMessage());

        if (candles.length == 0)
            return Mono.just(Message.INVALID_SYMBOL.getMessage());

        return Mono.just(oneDayRepository.save(new OneDay(symbol, TradingSignal.NONE, 0)).subscribe());
    }

    public Mono<Object> deleteSymbol(String symbol) {
        boolean symbolExists = Boolean.TRUE.equals(oneDayRepository.existsById(symbol).block());

        if (!symbolExists)
            return Mono.just(Message.SYMBOL_NOT_EXISTS.getMessage());

        return Mono.just(oneDayRepository.deleteById(symbol).subscribe());
    }

    public void refresh(String symbol) {
        OneDay oneDay = oneDayRepository.findById(symbol).block();

        if (oneDay == null)
            return;

        refresh(oneDay);
    }


    public void randomRefresh() {
        List<OneDay> items = this.oneDayRepository.findAll()
                .collectList()
                .block();

        refresh(randomItem(items));
    }

    private void refresh(OneDay oneDay) {
        float[] closingPrices = adapterService.closingPrices(binanceData.fetchOHLC(oneDay.symbol(), Timeframe.D1));

        TradingSignal smaSignal = smaStrategy.smaSignal(closingPrices);

        oneDayRepository.save(new OneDay(oneDay.symbol(), smaSignal, oneDay.version()))
                .subscribe();
    }

    private OneDay randomItem(List<OneDay> items) {
        return items.get(this.random.nextInt(0, items.size()));
    }
}
