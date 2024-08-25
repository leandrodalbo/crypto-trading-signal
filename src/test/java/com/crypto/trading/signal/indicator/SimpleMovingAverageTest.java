package com.crypto.trading.signal.indicator;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SimpleMovingAverageTest {

    @Autowired
    SimpleMovingAverage sma;

    @Value("${sma.shortPeriod}")
    private int shortSmaPeriod;

    @Value("${sma.longPeriod}")
    private int longSmaPeriod;

    @Test
    void willCalculateShortMAs() {
        float[] values = new float[]{3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f};

        double[] result = sma.shortSma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / shortSmaPeriod);

    }

    @Test
    void willCalculateLongMAs() {
        float[] values = new float[]{3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f};

        double[] result = sma.longSma(values);
        double sum = 0;

        for (float f : values)
            sum += f;

        assertThat(result.length).isEqualTo(1);
        assertThat(result[0]).isEqualTo(sum / longSmaPeriod);

    }
}
