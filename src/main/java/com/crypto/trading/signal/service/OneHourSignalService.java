package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OneHourSignalService {

    private final OneHourRepository oneHourRepository;
    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final AdapterService adapterService;

    public OneHourSignalService(OneHourRepository oneHourRepository, BinanceData binanceData, SmaStrategy smaStrategy, AdapterService adapterService) {
        this.oneHourRepository = oneHourRepository;
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.adapterService = adapterService;
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


    private void refresh(OneHour oneHour) {
        float[] closingPrices = adapterService.closingPrices(binanceData.fetchOHLC(oneHour.symbol(), Timeframe.H1));

        TradingSignal smaSignal = smaStrategy.smaSignal(closingPrices);

        oneHourRepository.save(new OneHour(oneHour.symbol(), smaSignal, oneHour.version()))
                .subscribe();
    }
}
