package com.crypto.trading.signal.service;

import com.crypto.trading.signal.indicator.SimpleMovingAverage;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class SmaService {

    private static final int LAST_TEN = 10;
    private final SimpleMovingAverage sma;

    public SmaService(SimpleMovingAverage sma) {
        this.sma = sma;
    }

    public TradingSignal smaSignal(float[] values) {
        double[] shortMAs = sma.shortSma(values);
        double[] longMAs = sma.longSma(values);

        if (shortMAs[shortMAs.length - 1] >= longMAs[longMAs.length - 1]) {
            int i = shortMAs.length - 2;
            int j = longMAs.length - 2;
            int count = 0;

            while (count < LAST_TEN) {
                if (shortMAs[i] < longMAs[j]) {
                    return TradingSignal.BUY;
                }
                i--;
                j--;
                count++;
            }
        }

        if (shortMAs[shortMAs.length - 1] < longMAs[longMAs.length - 1]) {
            int i = shortMAs.length - 2;
            int j = longMAs.length - 2;
            int count = 0;

            while (count < LAST_TEN) {
                if (shortMAs[i] > longMAs[j]) {
                    return TradingSignal.SELL;
                }
                i--;
                j--;
                count++;
            }

        }

        return TradingSignal.NONE;
    }
}
