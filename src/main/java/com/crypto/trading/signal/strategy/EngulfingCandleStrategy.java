package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

@Service
public class EngulfingCandleStrategy {

    public TradingSignal engulfingSignal(Candle[] candles) {
        if (candles.length == 0 || candles[candles.length - 1] == null || candles[candles.length - 2] == null || candles[candles.length - 3] == null)
            return TradingSignal.NONE;

        if (isBullish(candles[candles.length - 2], candles[candles.length - 1]) || isBullish(candles[candles.length - 3], candles[candles.length - 2]))
            return TradingSignal.BUY;

        if (isBearish(candles[candles.length - 2], candles[candles.length - 1]) || isBearish(candles[candles.length - 3], candles[candles.length - 2]))
            return TradingSignal.SELL;

        return TradingSignal.NONE;
    }

    private boolean isBullish(Candle previousCandle, Candle currentCandle) {
        return previousCandle.close() < previousCandle.open() &&
                currentCandle.close() > currentCandle.open() &&
                currentCandle.open() < previousCandle.close() &&
                currentCandle.close() > previousCandle.open();
    }

    private boolean isBearish(Candle previousCandle, Candle currentCandle) {
        return previousCandle.close() > previousCandle.open() &&
                currentCandle.close() < currentCandle.open() &&
                currentCandle.open() > previousCandle.close() &&
                currentCandle.close() < previousCandle.open();
    }
}
