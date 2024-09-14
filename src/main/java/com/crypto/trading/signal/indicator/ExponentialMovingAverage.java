package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExponentialMovingAverage {

    private static final int DEFAULT_SHORT_PERIOD = 12;
    private static final int DEFAULT_LONG_PERIOD = 26;

    private final ZeroCleaner zeroCleaner;
    private final Core core;

    @Value("${ema.shortPeriod}")
    private int shortEmaPeriod;

    @Value("${ema.longPeriod}")
    private int longEmaPeriod;

    public ExponentialMovingAverage(Core core, ZeroCleaner zeroCleaner) {
        this.zeroCleaner = zeroCleaner;
        this.core = core;
    }

    public double[] shortEma(float[] values) {
        int period = (shortEmaPeriod != 0) ? shortEmaPeriod : DEFAULT_SHORT_PERIOD;
        double[] result = new double[values.length];

        core.ema(0, values.length - 1, values, period, new MInteger(), new MInteger(), result);

        return zeroCleaner.cleanUp(result);
    }

    public double[] longEma(float[] values) {
        int period = (longEmaPeriod != 0) ? longEmaPeriod : DEFAULT_LONG_PERIOD;
        double[] result = new double[values.length];

        core.ema(0, values.length - 1, values, period, new MInteger(), new MInteger(), result);

        return zeroCleaner.cleanUp(result);
    }
}
