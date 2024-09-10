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
public class RSIStrategyTest {

    @Mock
    RelativeStrengthIndex rsi;

    @InjectMocks
    RSIStrategy service;

    @Test
    void willIdentifyABuyingSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                33, 29
        });
        assertThat(service.rsiSignal(new float[0])).isEqualTo(TradingSignal.BUY);
    }

    @Test
    void willIdentifySellingSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                62, 71
        });
        assertThat(service.rsiSignal(new float[0])).isEqualTo(TradingSignal.SELL);
    }

    @Test
    void willIdentifyThereIsNoSignal() {
        given(rsi.rsi(any())).willReturn(new double[]{
                33, 45
        });
        assertThat(service.rsiSignal(new float[0])).isEqualTo(TradingSignal.NONE);
    }
}
