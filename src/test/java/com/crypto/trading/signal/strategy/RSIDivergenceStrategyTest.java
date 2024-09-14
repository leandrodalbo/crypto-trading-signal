package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.RelativeStrengthIndex;
import com.crypto.trading.signal.model.TradingSignal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RSIDivergenceStrategyTest {

    @Mock
    RelativeStrengthIndex rsi;

    @InjectMocks
    RSIDiveregenceStrategy strategy;

    @Test
    void willIdentifyABuyingSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                52, 53, 59, 60, 61
        });
        assertThat(strategy.rsiDivergenceSignal(new float[]{14, 11, 10, 9, 9})).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void willIdentifySellingSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                33, 31, 26, 22, 22
        });
        assertThat(strategy.rsiDivergenceSignal(new float[]{16, 18, 22, 25, 25})).isEqualTo(TradingSignal.SELL);
    }

    @Test
    void willIdentifyThereIsNoSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                22, 25, 26, 22, 22
        });
        assertThat(strategy.rsiDivergenceSignal(new float[]{16, 18, 22, 25, 25})).isEqualTo(TradingSignal.NONE);

    }
}
