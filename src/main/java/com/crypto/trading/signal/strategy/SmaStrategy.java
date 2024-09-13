package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.SimpleMovingAverage;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class SmaStrategy {

    private static final int LAST_TEN = 10;
    private final SimpleMovingAverage sma;

    public SmaStrategy(SimpleMovingAverage sma) {
        this.sma = sma;
    }

    public TradingSignal smaSignal(float[] values) {
        double[] shortMAs = sma.shortSma(values);
        double[] longMAs = sma.longSma(values);

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
