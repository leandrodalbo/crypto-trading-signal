package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class OneDaySignalService {

    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final OneDayRepository oneDayRepository;
    private final AdapterService adapterService;

    public OneDaySignalService(BinanceData binanceData, SmaStrategy smaStrategy, OneDayRepository oneDayRepository, AdapterService adapterService) {
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.oneDayRepository = oneDayRepository;
        this.adapterService = adapterService;
    }

    public Flux<OneDay> getAll() {
        return this.oneDayRepository.findAll();
    }


    public void refresh(String symbol) throws InvalidSymbolException {
        OneDay oneDay = oneDayRepository.findById(symbol).block();

        if (oneDay == null)
            throw new InvalidSymbolException();

        refresh(oneDay);
    }


    private void refresh(OneDay oneDay) {
        float[] closingPrices = adapterService.closingPrices(binanceData.fetchOHLC(oneDay.symbol(), Timeframe.D1));

        TradingSignal smaSignal = smaStrategy.smaSignal(closingPrices);

        oneDayRepository.save(new OneDay(oneDay.symbol(), smaSignal, oneDay.version()))
                .subscribe();
    }
}
