package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMovingAverageTest {

    SimpleMovingAverage sma = new SimpleMovingAverage(new Core(), new ZeroCleaner());

    @Test
    void willCalculateShortMAs() {
        float[] values = new float[]{3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f};

        double[] result = sma.shortSma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / 9);

    }

    @Test
    void willCalculateLongMAs() {
        float[] values = new float[]{3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f};

        double[] result = sma.longSma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / 21);

    }
}
