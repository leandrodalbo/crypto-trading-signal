package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.MACDIndicator;
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
public class MACDStrategyTest {

    @Mock
    MACDIndicator indicator;

    @InjectMocks
    MACDStrategy strategy;

    @Test
    void willIdentifyABuyingSignal() {
        given(indicator.macdAndSignal(any())).willReturn(Map.of(
                MACDIndicator.MACD_KEY, new double[]{2.0, 2.1, 2.4},
                MACDIndicator.SIGNAL_KEY, new double[]{2.1, 2.1, 2.3}
        ));
        assertThat(strategy.macdSignal(new float[0])).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void willIdentifySellingSignal() {
        given(indicator.macdAndSignal(any())).willReturn(Map.of(
                MACDIndicator.MACD_KEY, new double[]{2.1, 2.1, 2.2},
                MACDIndicator.SIGNAL_KEY, new double[]{2.0, 2.1, 2.3}
        ));
        assertThat(strategy.macdSignal(new float[0])).isEqualTo(TradingSignal.SELL);
    }

    @Test
    void willIdentifyThereIsNoSignal() {
        given(indicator.macdAndSignal(any())).willReturn(Map.of(
                MACDIndicator.MACD_KEY, new double[]{2.1, 2.1, 2.2},
                MACDIndicator.SIGNAL_KEY, new double[]{2.2, 2.1, 2.2}
        ));
        assertThat(strategy.macdSignal(new float[0])).isEqualTo(TradingSignal.NONE);
    }
}
