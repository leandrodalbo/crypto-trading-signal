package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.errorhandler.exeptions.SymbolAlreadyExistsException;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
public class OneHourSignalService {

    private final OneHourRepository oneHourRepository;
    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final Random random;
    private final AdapterService adapterService;

    public OneHourSignalService(OneHourRepository oneHourRepository, BinanceData binanceData, SmaStrategy smaStrategy, Random random, AdapterService adapterService) {
        this.oneHourRepository = oneHourRepository;
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.random = random;
        this.adapterService = adapterService;
    }

    public Mono<OneHour> getById(String symbol) {
        return oneHourRepository.findById(symbol);
    }

    public Flux<OneHour> getAll() {
        return oneHourRepository.findAll();
    }

    public void refresh(String symbol) throws InvalidSymbolException {
        OneHour oneHour = oneHourRepository.findById(symbol).block();

        if (oneHour == null)
            throw new InvalidSymbolException();

        refresh(oneHour);
    }

    public void randomRefresh() {
        List<OneHour> items = this.oneHourRepository.findAll()
                .collectList()
                .block();

        refresh(randomItem(items));
    }

    private void refresh(OneHour oneHour) {
        float[] closingPrices = adapterService.closingPrices(binanceData.fetchOHLC(oneHour.symbol(), Timeframe.H1));

        TradingSignal smaSignal = smaStrategy.smaSignal(closingPrices);

        oneHourRepository.save(new OneHour(oneHour.symbol(), smaSignal, oneHour.version()))
                .subscribe();
    }

    public void saveNewSymbol(String symbol) throws InvalidSymbolException, SymbolAlreadyExistsException {
        boolean symbolExists = Boolean.TRUE.equals(oneHourRepository.existsById(symbol).block());
        Candle[] candles = binanceData.fetchOHLC(symbol, Timeframe.H1).block();

        if (symbolExists)
            throw new SymbolAlreadyExistsException();

        if (candles.length == 0)
            throw new InvalidSymbolException();

        oneHourRepository.save(new OneHour(symbol, TradingSignal.NONE, 0)).subscribe();
    }

    public void deleteSymbol(String symbol) throws InvalidSymbolException {
        boolean symbolExists = Boolean.TRUE.equals(oneHourRepository.existsById(symbol).block());

        if (!symbolExists)
            throw new InvalidSymbolException();

        oneHourRepository.deleteById(symbol).subscribe();
    }


    private OneHour randomItem(List<OneHour> items) {
        return items.get(this.random.nextInt(0, items.size()));
    }
}
