package com.crypto.trading.signal.entity;

import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("onehour")
public record OneHour(

        @Id
        @Column("symbol")
        String symbol,

        @Column("signaltime")
        long signalTime,

        @Column("buystrength")
        SignalStrength buyStrength,

        @Column("sellstrength")
        SignalStrength sellStrength,

        @Column("bollingerbands")
        TradingSignal bollingerBands,

        @Column("ema")
        TradingSignal ema,

        @Column("sma")
        TradingSignal sma,

        @Column("macd")
        TradingSignal macd,

        @Column("obv")
        TradingSignal obv,

        @Column("rsi")
        TradingSignal rsi,

        @Column("rsidivergence")
        TradingSignal rsiDivergence,

        @Column("stochastic")
        TradingSignal stochastic,

        @Column("engulfingcandle")
        TradingSignal engulfingCandle,

        @Version
        @Column("version")
        Integer version
) {
    public static OneHour fromSignal(Signal signal, Integer version) {
        return new OneHour(signal.symbol(),
                Instant.now().toEpochMilli(),
                signal.buyStrength(),
                signal.sellStrength(),
                signal.bollingerBands(),
                signal.ema(),
                signal.sma(),
                signal.macd(),
                signal.obv(),
                signal.rsi(),
                signal.rsiDivergence(),
                signal.stochastic(),
                signal.engulfingCandle(),
                version);
    }
}
