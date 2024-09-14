package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StochasticIndicatorTest {

    StochasticIndicator indicator = new StochasticIndicator(new Core(), new ZeroCleaner());

    @Test
    void willCalculateStochasticIndicatorValues() {
        float[] high = new float[]{31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 22.9f, 23.1f, 21.2f, 6.1f, 43.4f, 21.1f, 44.2f, 25.1f, 21.3f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f};
        float[] low = new float[]{31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 22.9f, 23.1f, 21.2f, 6.1f, 43.4f, 21.1f, 44.2f, 25.1f, 21.3f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f};
        float[] close = new float[]{31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 22.9f, 23.1f, 21.2f, 6.1f, 43.4f, 21.1f, 44.2f, 25.1f, 21.3f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f};

        Map<String, double[]> result = indicator.stochasticValues(high, low, close);

        double[] kValues = result.get(StochasticIndicator.STOCH_k_KEY);
        double[] dValues = result.get(StochasticIndicator.STOCH_D_KEY);

        assertThat(kValues[kValues.length - 1]).isNotEqualTo(0.0);
        assertThat(kValues[kValues.length - 3]).isNotEqualTo(0.0);
        assertThat(dValues[dValues.length - 1]).isNotEqualTo(0.0);
        assertThat(dValues[dValues.length - 3]).isNotEqualTo(0.0);

    }
}
