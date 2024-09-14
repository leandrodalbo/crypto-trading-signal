package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.OnBalanceVolume;
import com.crypto.trading.signal.indicator.StochasticIndicator;
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
public class StochasticIndicatorStrategyTest {

    @Mock
    StochasticIndicator indicator;

    @InjectMocks
    StochasticIndicatorStrategy strategy;

    @Test
    void willIdentifyABuyingSignal() {
        given(indicator.stochasticValues(any(), any(), any())).willReturn(Map.of(
                StochasticIndicator.STOCH_k_KEY, new double[]{
                        20, 24, 22, 21, 18
                },
                StochasticIndicator.STOCH_D_KEY, new double[]{
                        23, 21, 23, 25, 16
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0], new float[0])).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void willIdentifySellingSignal() {
        given(indicator.stochasticValues(any(), any(), any())).willReturn(Map.of(
                StochasticIndicator.STOCH_k_KEY, new double[]{
                        78, 74, 82, 77, 82
                },
                StochasticIndicator.STOCH_D_KEY, new double[]{
                        81, 78, 77, 79, 84
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0], new float[0])).isEqualTo(TradingSignal.SELL);

    }

    @Test
    void willIdentifyThereIsNoSignal() {
        given(indicator.stochasticValues(any(), any(), any())).willReturn(Map.of(
                StochasticIndicator.STOCH_k_KEY, new double[]{
                        78, 74, 82, 77, 82
                },
                StochasticIndicator.STOCH_D_KEY, new double[]{
                        81, 78, 77, 79, 80
                }));
        assertThat(strategy.obvSignal(new float[0], new float[0], new float[0])).isEqualTo(TradingSignal.NONE);
    }
}
