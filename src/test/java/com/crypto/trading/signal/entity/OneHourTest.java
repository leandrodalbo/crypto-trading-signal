package com.crypto.trading.signal.entity;

import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneHourTest {

    @Test
    public void willCreateAnInstanceFromASignal() {
        int version = 1;
        Signal signal = new Signal("BTCUSDT", Timeframe.D1, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);

        OneHour oneHour = OneHour.fromSignal(signal, version);

        assertThat(oneHour.symbol()).isEqualTo(signal.symbol());
        assertThat(oneHour.bollingerBands()).isEqualTo(signal.bollingerBands());
        assertThat(oneHour.ema()).isEqualTo(signal.ema());
        assertThat(oneHour.sma()).isEqualTo(signal.sma());
        assertThat(oneHour.macd()).isEqualTo(signal.macd());
        assertThat(oneHour.obv()).isEqualTo(signal.obv());
        assertThat(oneHour.rsi()).isEqualTo(signal.rsi());
        assertThat(oneHour.rsiDivergence()).isEqualTo(signal.rsiDivergence());
        assertThat(oneHour.stochastic()).isEqualTo(signal.stochastic());
        assertThat(oneHour.engulfingCandle()).isEqualTo(signal.engulfingCandle());
        assertThat(oneHour.version()).isEqualTo(version);
    }
}
