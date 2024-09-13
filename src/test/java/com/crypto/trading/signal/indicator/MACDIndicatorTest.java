package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MACDIndicatorTest {

    MACDIndicator macdIndicator = new MACDIndicator(new Core(), new ZeroCleaner());

    @Test
    void willCalculateMACDAndSignal() {
        float[] values = new float[]{43.4f, 28.1f, 46.2f, 43.4f, 28.1f, 46.2f, 54.1f, 21.3f, 15.4f, 12.1f, 34.2f, 14.1f, 22.3f, 43.4f, 21.1f, 44.2f, 45.1f, 11.3f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 22.9f, 23.1f, 21.2f, 6.1f, 43.4f, 21.1f, 44.2f, 25.1f, 21.3f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f};

        Map<String, double[]> result = macdIndicator.macdAndSignal(values);

        double[] macd = result.get(MACDIndicator.MACD_KEY);
        double[] signal = result.get(MACDIndicator.SIGNAL_KEY);

        assertThat(macd[macd.length - 1]).isNotEqualTo(0.0);
        assertThat(macd[macd.length - 3]).isNotEqualTo(0.0);
        assertThat(macd[signal.length - 1]).isNotEqualTo(0.0);
        assertThat(macd[signal.length - 3]).isNotEqualTo(0.0);
    }
}
