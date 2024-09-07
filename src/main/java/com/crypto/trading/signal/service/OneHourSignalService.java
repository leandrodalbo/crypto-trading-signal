package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.fetchdata.BinanceData;
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

    public void refresh(String symbol) {
        OneHour oneHour = oneHourRepository.findById(symbol).block();

        if (oneHour == null)
            return;

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

    private OneHour randomItem(List<OneHour> items) {
        return items.get(this.random.nextInt(0, items.size()));
    }
}
