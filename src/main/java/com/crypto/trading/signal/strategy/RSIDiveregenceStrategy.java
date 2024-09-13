package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.RelativeStrengthIndex;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class RSIDiveregenceStrategy {

    private final RelativeStrengthIndex indicator;

    public RSIDiveregenceStrategy(RelativeStrengthIndex rsi) {
        this.indicator = rsi;
    }

    public TradingSignal rsiDivergenceSignal(float[] values) {
        double[] rsiValues = this.indicator.rsi(values);

        if (values[values.length - 10] > values[values.length - 5] && values[values.length - 5] > values[values.length - 1] && rsiValues[rsiValues.length - 10] < rsiValues[rsiValues.length - 5] && rsiValues[rsiValues.length - 5] < rsiValues[rsiValues.length - 1]) {
            return TradingSignal.BUY;
        }

        if (values[values.length - 10] < values[values.length - 5] && values[values.length - 5] < values[values.length - 1] && rsiValues[rsiValues.length - 10] > rsiValues[rsiValues.length - 5] && rsiValues[rsiValues.length - 5] > rsiValues[rsiValues.length - 1]) {
            return TradingSignal.SELL;
        }

        return TradingSignal.NONE;
    }
}
