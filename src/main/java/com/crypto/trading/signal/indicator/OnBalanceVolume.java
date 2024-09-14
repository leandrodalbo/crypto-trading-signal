package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OnBalanceVolume {

    public static final String OBV_KEY = "OBV";
    public static final String OBV_MA_KEY = "OBV_MA";

    private static final int MA_PERIOD = 20;

    private final Core core;
    private final ZeroCleaner zeroCleaner;

    @Value("${obv.maperiod}")
    private int obvMaPeriod;

    public OnBalanceVolume(Core core, ZeroCleaner zeroCleaner) {
        this.core = core;
        this.zeroCleaner = zeroCleaner;
    }

    public Map<String, double[]> obv(float[] values, float[] volumes) {
        int period = (obvMaPeriod != 0) ? obvMaPeriod : MA_PERIOD;

        double[] obv = new double[values.length];
        double[] obvma = new double[values.length];

        core.obv(0, values.length - 1, values, volumes, new MInteger(), new MInteger(), obv);
        core.sma(0, obv.length - 1, obv, period, new MInteger(), new MInteger(), obvma);

        obv = zeroCleaner.cleanUp(obv);
        obvma = zeroCleaner.cleanUp(obvma);

        return Map.of(OBV_KEY, obv, OBV_MA_KEY, obvma);
    }
}
