package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.RelativeStrengthIndex;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class RSIStrategy {

    private static final int SELL_ZONE = 70;
    private static final int BUY_ZONE = 30;
    private final RelativeStrengthIndex indicator;

    public RSIStrategy(RelativeStrengthIndex rsi) {
        this.indicator = rsi;
    }

    public TradingSignal rsiSignal(float[] values) {

        double[] rsiValues = indicator.rsi(values);

        if (rsiValues[rsiValues.length - 1] > SELL_ZONE)
            return TradingSignal.SELL;

        if (rsiValues[rsiValues.length - 1] < BUY_ZONE)
            return TradingSignal.BUY;

        return TradingSignal.NONE;
    }
}
