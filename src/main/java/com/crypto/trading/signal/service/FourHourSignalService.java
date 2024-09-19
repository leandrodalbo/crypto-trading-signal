package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.FourHourRepository;
import com.crypto.trading.signal.repository.OneHourRepository;
import com.crypto.trading.signal.service.adapter.AdapterService;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FourHourSignalService {

    private final FourHourRepository fourHourRepository;
    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final AdapterService adapterService;

    public FourHourSignalService(FourHourRepository fourHourRepository, BinanceData binanceData, SmaStrategy smaStrategy, AdapterService adapterService) {
        this.fourHourRepository = fourHourRepository;
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.adapterService = adapterService;
    }

    public Flux<FourHour> getAll() {
        return fourHourRepository.findAll();
    }

    public void refresh(String symbol) throws InvalidSymbolException {
        FourHour fourHour = fourHourRepository.findById(symbol).block();

        if (fourHour == null)
            throw new InvalidSymbolException();

        refresh(fourHour);
    }


    private void refresh(FourHour fourHour) {
        float[] closingPrices = adapterService.closingPrices(binanceData.fetchOHLC(fourHour.symbol(), Timeframe.H1));

        TradingSignal smaSignal = smaStrategy.smaSignal(closingPrices);

        fourHourRepository.save(new FourHour(fourHour.symbol(), smaSignal, fourHour.version()))
                .subscribe();
    }
}
