package com.crypto.trading.signal.entity;

import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ONEDAY")
public record OneDay(

        @Id
        @Column("SYMBOL")
        String symbol,

        @Column("SMACROSSOVER")
        TradingSignal smaCrossover,

        @Version
        @Column("VERSION")
        Integer version
) {
}
