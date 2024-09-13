package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RelativeStrengthIndex {

    private static final int DEFAULT_PERIOD = 14;

    private final Core core;
    private final ZeroCleaner zeroCleaner;
    @Value("${rsi.period}")
    private int rsiPeriod;

    public RelativeStrengthIndex(Core core, ZeroCleaner zeroCleaner) {
        this.core = core;
        this.zeroCleaner = zeroCleaner;
    }

    public double[] rsi(float[] values) {
        int period = (rsiPeriod != 0) ? rsiPeriod : DEFAULT_PERIOD;
        double[] result = new double[values.length];

        core.rsi(0, values.length - 1, values, period, new MInteger(), new MInteger(), result);

        return zeroCleaner.cleanUp(result);
    }

}
