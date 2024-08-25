package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleMovingAverage {

    private final Core core;

    @Value("${sma.shortPeriod}")
    private int shortSmaPeriod;

    @Value("${sma.longPeriod}")
    private int longSmaPeriod;


    public SimpleMovingAverage(Core core) {
        this.core = core;
    }

    public double[] shortSma(float[] values) {
        double[] result = new double[values.length - (shortSmaPeriod - 1)];

        core.sma(0, values.length - 1, values, shortSmaPeriod, new MInteger(), new MInteger(), result);

        return result;
    }

    public double[] longSma(float[] values) {
        double[] result = new double[values.length - (longSmaPeriod - 1)];

        core.sma(0, values.length - 1, values, longSmaPeriod, new MInteger(), new MInteger(), result);

        return result;
    }
}
