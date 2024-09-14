package com.crypto.trading.signal.indicator;


import com.tictactec.ta.lib.Core;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OnBalanceVolumeTest {

    OnBalanceVolume indicator = new OnBalanceVolume(new Core(), new ZeroCleaner());

    @Test
    void willCalculateOBVAndOBVMA() {
        float[] values = new float[]{31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 33.4f, 11.1f, 44.2f, 45.1f, 21.3f, 22.9f, 33.4f, 11.1f, 44.2f, 55.1f, 11.3f, 22.9f, 23.1f, 21.2f, 6.1f, 43.4f, 21.1f, 44.2f, 25.1f, 21.3f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f, 15.1f, 45.2f, 33.4f, 11.1f, 23.4f, 21.1f, 44.2f, 45.1f, 11.3f, 31.4f};
        float[] volumes = new float[]{314f, 151f, 452f, 334f, 111f, 442f, 451f, 213f, 229f, 334f, 111f, 442f, 551f, 113f, 334f, 111f, 442f, 451f, 213f, 229f, 334f, 111f, 442f, 551f, 113f, 229f, 231f, 212f, 61f, 434f, 211f, 442f, 251f, 213f, 234f, 211f, 442f, 451f, 113f, 314f, 151f, 452f, 334f, 111f, 234f, 211f, 442f, 451f, 113f, 314f};

        Map<String, double[]> result = indicator.obv(values, volumes);

        double[] obv = result.get(OnBalanceVolume.OBV_KEY);
        double[] obvMa = result.get(OnBalanceVolume.OBV_MA_KEY);

        assertThat(obv[obv.length - 1]).isNotEqualTo(0.0);
        assertThat(obv[obv.length - 3]).isNotEqualTo(0.0);
        assertThat(obvMa[obvMa.length - 1]).isNotEqualTo(0.0);
        assertThat(obvMa[obvMa.length - 3]).isNotEqualTo(0.0);

    }
}
