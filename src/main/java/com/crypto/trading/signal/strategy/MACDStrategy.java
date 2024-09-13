package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.MACDIndicator;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MACDStrategy {

    private final MACDIndicator macdIndicator;

    public MACDStrategy(MACDIndicator macd) {
        this.macdIndicator = macd;
    }

    public TradingSignal macdSignal(float[] values) {
        Map<String, double[]> macdAndSignal = this.macdIndicator.macdAndSignal(values);
        double[] macd = macdAndSignal.get(MACDIndicator.MACD_KEY);
        double[] signal = macdAndSignal.get(MACDIndicator.SIGNAL_KEY);

        if (macd[macd.length - 3] < signal[signal.length - 3] && macd[macd.length - 1] > signal[signal.length - 1]) {
            return TradingSignal.BUY;
        }

        if (macd[macd.length - 3] > signal[signal.length - 3] && macd[macd.length - 1] < signal[signal.length - 1]) {
            return TradingSignal.SELL;
        }

        return TradingSignal.NONE;
    }
}
