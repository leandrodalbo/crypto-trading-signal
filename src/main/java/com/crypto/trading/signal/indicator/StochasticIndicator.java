package com.crypto.trading.signal.indicator;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StochasticIndicator {

    public static final String STOCH_k_KEY = "K";
    public static final String STOCH_D_KEY = "D";

    private static final int DEFAULT_FAST_K_PERIOD = 14;
    private static final int DEFAULT_SLOW_K_PERIOD = 3;
    private static final int DEFAULT_SLOW_D_PERIOD = 3;

    private final Core core;
    private final ZeroCleaner zeroCleaner;

    @Value("${stoch.fastKPerdiod}")
    private int fastKPeriod;

    @Value("${stoch.slowKPerdiod}")
    private int slowKPeriod;

    @Value("${stoch.slowDPerdiod}")
    private int slowDPeriod;

    public StochasticIndicator(Core core, ZeroCleaner zeroCleaner) {
        this.core = core;
        this.zeroCleaner = zeroCleaner;
    }

    public Map<String, double[]> stochasticValues(float[] high, float[] low, float[] close) {
        int fastK = (fastKPeriod != 0) ? fastKPeriod : DEFAULT_FAST_K_PERIOD;
        int slowK = (slowKPeriod != 0) ? slowKPeriod : DEFAULT_SLOW_K_PERIOD;
        int slowD = (slowDPeriod != 0) ? slowDPeriod : DEFAULT_SLOW_D_PERIOD;

        double[] stochasticK = new double[high.length];
        double[] stochasticD = new double[high.length];

        core.stoch(0,
                high.length - 1,
                high,
                low,
                close,
                fastK,
                slowK,
                MAType.Sma,
                slowD,
                MAType.Sma,
                new MInteger(),
                new MInteger(),
                stochasticK,
                stochasticD);

        stochasticK = zeroCleaner.cleanUp(stochasticK);
        stochasticD = zeroCleaner.cleanUp(stochasticD);

        return Map.of(STOCH_k_KEY, stochasticK, STOCH_D_KEY, stochasticD);
    }
}
