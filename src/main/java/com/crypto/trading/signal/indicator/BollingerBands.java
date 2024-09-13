package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BollingerBands {

    public static final String UPPER_BAND_KEY = "UPPER";
    public static final String MIDDLE_BAND_KEY = "MIDDLE";
    public static final String LOWER_BAND_KEY = "LOWER";
    private static final int DEFAULT_PERIOD = 20;
    private static final double STD_DEV = 2;
    private final Core core;
    private final ZeroCleaner zeroCleaner;
    @Value("${bbands.period}")
    private int bandsPeriod;
    @Value("${bbands.stddev}")
    private double stdDev;

    public BollingerBands(Core core, ZeroCleaner zeroCleaner) {
        this.core = core;
        this.zeroCleaner = zeroCleaner;
    }

    public Map<String, Double> bands(float[] values) {
        int period = (bandsPeriod != 0) ? bandsPeriod : DEFAULT_PERIOD;
        double upAndDown = (stdDev != 0) ? stdDev : STD_DEV;

        double[] upperBand = new double[values.length];
        double[] middleBand = new double[values.length];
        double[] lowerBand = new double[values.length];

        core.bbands(0, values.length - 1, values, period, upAndDown, upAndDown, MAType.Sma, new MInteger(), new MInteger(), upperBand, middleBand, lowerBand);

        upperBand = zeroCleaner.cleanUp(upperBand);
        middleBand = zeroCleaner.cleanUp(middleBand);
        lowerBand = zeroCleaner.cleanUp(lowerBand);

        return Map.of(UPPER_BAND_KEY, upperBand[upperBand.length - 1], MIDDLE_BAND_KEY, middleBand[middleBand.length - 1], LOWER_BAND_KEY, lowerBand[lowerBand.length - 1]);
    }
}
