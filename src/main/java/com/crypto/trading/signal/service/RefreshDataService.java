package com.crypto.trading.signal.service;

import com.crypto.trading.signal.conf.CryptoDataConf;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.fetchdata.BinanceData;
import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RefreshDataService {


    private final BinanceData binanceData;
    private final CryptoDataConf conf;
    private final SmaService smaService;
    private final OneDayRepository oneDayRepository;

    public RefreshDataService(BinanceData binanceData, CryptoDataConf conf, SmaService smaService, OneDayRepository oneDayRepository) {
        this.binanceData = binanceData;
        this.conf = conf;
        this.smaService = smaService;
        this.oneDayRepository = oneDayRepository;
    }

    public void refreshOneDaySymbols() {

        for (String symbol : conf.symbols()) {
            binanceData.fetchOHLC(symbol, Timeframe.D1)
                    .subscribe(it -> {
                        List<Float> closingPrice = Arrays.stream(it).map(Candle::close)
                                .toList();
                        float[] prices = new float[closingPrice.size()];

                        for (int i = 0; i < prices.length; i++)
                            prices[i] = closingPrice.get(i);

                        oneDayRepository.save(new OneDay(symbol, smaService.smaSignal(prices), null))
                                .subscribe();

                    });
        }

    }
}
