package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RelativeStrengthIndexTest {

    RelativeStrengthIndex rsi = new RelativeStrengthIndex(new Core(), new ZeroCleaner());

    @Test
    void willCalculateRSIValues() {
        float[] values = new float[]{3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.4f, 1.1f, 4.2f, 5.1f, 1.3f, 2.9f, 3.1f, 1.2f, 6.1f};

        double[] result = rsi.rsi(values);

        assertThat(result.length).isEqualTo(values.length - 14);
        assertThat(result[result.length - 1]).isGreaterThan(0);

    }
}
