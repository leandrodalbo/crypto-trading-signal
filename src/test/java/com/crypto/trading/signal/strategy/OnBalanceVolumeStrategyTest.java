package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.BollingerBands;
import com.crypto.trading.signal.indicator.OnBalanceVolume;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OnBalanceVolumeStrategyTest {

    @Mock
    OnBalanceVolume indicator;

    @InjectMocks
    OnBalanceVolumeStrategy strategy;

    @Test
    void willIdentifyABuyingSignal() {
        given(indicator.obv(any(), any())).willReturn(Map.of(
                OnBalanceVolume.OBV_KEY, new double[]{
                        10, 10, 10, 10, 20
                },
                OnBalanceVolume.OBV_MA_KEY, new double[]{
                        15, 11, 15, 10, 15
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0])).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void willIdentifySellingSignal() {
        given(indicator.obv(any(), any())).willReturn(Map.of(
                OnBalanceVolume.OBV_KEY, new double[]{
                        15, 10, 10, 10, 10
                },
                OnBalanceVolume.OBV_MA_KEY, new double[]{
                        10, 11, 15, 10, 15
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0])).isEqualTo(TradingSignal.SELL);

    }

    @Test
    void willIdentifyThereIsNoSignal() {
        given(indicator.obv(any(), any())).willReturn(Map.of(
                OnBalanceVolume.OBV_KEY, new double[]{
                        15, 10, 10, 10, 10
                },
                OnBalanceVolume.OBV_MA_KEY, new double[]{
                        10, 11, 15, 10, 10
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0])).isEqualTo(TradingSignal.NONE);

    }
}
