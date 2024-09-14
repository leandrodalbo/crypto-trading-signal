package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EngulfingCandleStrategyTest {
    EngulfingCandleStrategy strategy = new EngulfingCandleStrategy();

    @Test
    void shouldFindABullishPatternBetweenTheFirstASecond() {
        Candle[] candles = new Candle[]{
                Candle.of(2f, 2.5f, 1.2f, 1.5f),
                Candle.of(1.4f, 3.0f, 1.2f, 2.1f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
        };
        assertThat(strategy.engulfingSignal(candles)).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void shouldFindABullishPatternBetweenTheSecondAndLast() {
        Candle[] candles = new Candle[]{
                Candle.of(1.1f, 2.2f, 1f, 1.0f),
                Candle.of(3.1f, 3.5f, 2.3f, 2.4f),
                Candle.of(2.1f, 3.4f, 1.9f, 3.2f),
        };
        assertThat(strategy.engulfingSignal(candles)).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void shouldFindABearishPatternBetweenTheFirstASecond() {
        Candle[] candles = new Candle[]{
                Candle.of(2f, 2.5f, 1.2f, 2.5f),
                Candle.of(2.8f, 3.0f, 1.2f, 1.9f),
                Candle.of(0.9f, 5.0f, 0.8f, 2.5f),
        };
        assertThat(strategy.engulfingSignal(candles)).isEqualTo(TradingSignal.SELL);
    }

    @Test
    void shouldFindABearishPatternBetweenTheSecondAndLast() {
        Candle[] candles = new Candle[]{
                Candle.of(1.1f, 2.2f, 1f, 1.0f),
                Candle.of(3.1f, 3.5f, 2.3f, 3.6f),
                Candle.of(4.1f, 3.4f, 1.9f, 3.0f),
        };
        assertThat(strategy.engulfingSignal(candles)).isEqualTo(TradingSignal.SELL);
    }

    @Test
    void shouldBeFalseForNullCandles() {
        assertThat(strategy.engulfingSignal(new Candle[2])).isEqualTo(TradingSignal.NONE);
    }

    @Test
    void shouldBeFalseForAnEmptyArray() {
        assertThat(strategy.engulfingSignal(new Candle[0])).isEqualTo(TradingSignal.NONE);
    }
}
