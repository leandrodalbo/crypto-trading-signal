package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.strategy.SmaStrategy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class OneDaySignalService {

    private final BinanceData binanceData;
    private final SmaStrategy smaStrategy;
    private final OneDayRepository oneDayRepository;

    public OneDaySignalService(BinanceData binanceData, SmaStrategy smaStrategy, OneDayRepository oneDayRepository) {
        this.binanceData = binanceData;
        this.smaStrategy = smaStrategy;
        this.oneDayRepository = oneDayRepository;
    }

    public Flux<OneDay> getAll() {
        return this.oneDayRepository.findAll();
    }

    public void refresh() {
        List<OneDay> items = this.oneDayRepository.findAll()
                .collectList()
                .block();

        OneDay oneDay = randomItem(items);

        binanceData.fetchOHLC(oneDay.symbol(), Timeframe.D1)
                .subscribe(candles -> {
                    float[] closingPrices = toPrimitiveArray(Arrays.stream(candles).map(Candle::close)
                            .toList());

                    TradingSignal tradingSignal = smaStrategy.smaSignal(closingPrices);

                    oneDayRepository.save(new OneDay(oneDay.symbol(), tradingSignal, oneDay.version()))
                            .subscribe();
                });

    }

    private float[] toPrimitiveArray(List<Float> items) {
        float[] result = new float[items.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = items.get(i);

        return result;
    }

    private OneDay randomItem(List<OneDay> items) {
        Random random = new Random();
        return items.get(random.nextInt(0, items.size()));
    }
}
