package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExponentialMovingAverageTest {

    ExponentialMovingAverage sma = new ExponentialMovingAverage(new Core(), new ZeroCleaner());

    @Test
    void willCalculateShortMAs() {
        float[] values = new float[]{3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f,};

        double[] result = sma.shortEma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / 12);

    }

    @Test
    void willCalculateLongMAs() {
        float[] values = new float[]{3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f, 2.9f, 3.1f, 1.2f, 6.1f, 6.1f};

        double[] result = sma.longEma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / 26);

    }
}
