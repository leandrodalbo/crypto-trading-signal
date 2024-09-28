package com.crypto.trading.signal.entity;

import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FourHourTest {

    @Test
    public void willCreateAnInstanceFromASignal() {
        int version = 1;
        Signal signal = new Signal("BTCUSDT", Timeframe.D1, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL, TradingSignal.BUY, TradingSignal.NONE, TradingSignal.SELL);

        FourHour fourHour = FourHour.fromSignal(signal, version);

        assertThat(fourHour.symbol()).isEqualTo(signal.symbol());
        assertThat(fourHour.bollingerBands()).isEqualTo(signal.bollingerBands());
        assertThat(fourHour.ema()).isEqualTo(signal.ema());
        assertThat(fourHour.sma()).isEqualTo(signal.sma());
        assertThat(fourHour.macd()).isEqualTo(signal.macd());
        assertThat(fourHour.obv()).isEqualTo(signal.obv());
        assertThat(fourHour.rsi()).isEqualTo(signal.rsi());
        assertThat(fourHour.rsiDivergence()).isEqualTo(signal.rsiDivergence());
        assertThat(fourHour.stochastic()).isEqualTo(signal.stochastic());
        assertThat(fourHour.engulfingCandle()).isEqualTo(signal.engulfingCandle());
        assertThat(fourHour.version()).isEqualTo(version);
    }
}
