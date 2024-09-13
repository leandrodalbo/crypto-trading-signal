package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.BollingerBands;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BollingBandsStrategy {

    private final BollingerBands indicator;

    public BollingBandsStrategy(BollingerBands bollingerBands) {
        this.indicator = bollingerBands;
    }

    public TradingSignal bollingerBandsSignal(float[] values, float price) {

        Map<String, Double> bands = indicator.bands(values);

        if (price >= bands.get(BollingerBands.UPPER_BAND_KEY))
            return TradingSignal.SELL;

        if (price <= bands.get(BollingerBands.LOWER_BAND_KEY))
            return TradingSignal.BUY;

        return TradingSignal.NONE;
    }
}
