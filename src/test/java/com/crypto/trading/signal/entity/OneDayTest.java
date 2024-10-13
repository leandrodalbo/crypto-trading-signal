package com.crypto.trading.signal.entity;

import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneDayTest {

    @Test
    public void willCreateAnInstanceFromASignal() {
        int version = 1;
        Signal signal = new Signal("BTCUSDT", Timeframe.D1, SignalStrength.STRONG, SignalStrength.STRONG, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);

        OneDay oneDay = OneDay.fromSignal(signal, version);

        assertThat(oneDay.symbol()).isEqualTo(signal.symbol());
        assertThat(oneDay.bollingerBands()).isEqualTo(signal.bollingerBands());
        assertThat(oneDay.ema()).isEqualTo(signal.ema());
        assertThat(oneDay.sma()).isEqualTo(signal.sma());
        assertThat(oneDay.macd()).isEqualTo(signal.macd());
        assertThat(oneDay.obv()).isEqualTo(signal.obv());
        assertThat(oneDay.rsi()).isEqualTo(signal.rsi());
        assertThat(oneDay.rsiDivergence()).isEqualTo(signal.rsiDivergence());
        assertThat(oneDay.stochastic()).isEqualTo(signal.stochastic());
        assertThat(oneDay.engulfingCandle()).isEqualTo(signal.engulfingCandle());
        assertThat(oneDay.lindaMacd()).isEqualTo(signal.lindaMACD());
        assertThat(oneDay.turtleSignal()).isEqualTo(signal.turtleSignal());
        assertThat(oneDay.hammerAndShootingCandle()).isEqualTo(signal.hammerAndShootingStars());
        assertThat(oneDay.version()).isEqualTo(version);
    }
}
