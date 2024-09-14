package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.ExponentialMovingAverage;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class EmaStrategy {

    private final ExponentialMovingAverage ema;

    public EmaStrategy(ExponentialMovingAverage ema) {
        this.ema = ema;
    }

    public TradingSignal emaSignal(float[] values) {
        double[] shortMAs = ema.shortEma(values);
        double[] longMAs = ema.longEma(values);

        if (shortMAs[shortMAs.length - 3] < longMAs[longMAs.length - 3] &&
                shortMAs[shortMAs.length - 1] > longMAs[longMAs.length - 1]
        ) {
            return TradingSignal.BUY;
        }

        if (shortMAs[shortMAs.length - 3] > longMAs[longMAs.length - 3] &&
                shortMAs[shortMAs.length - 1] < longMAs[longMAs.length - 1]
        ) {
            return TradingSignal.SELL;
        }
        return TradingSignal.NONE;
    }
}
