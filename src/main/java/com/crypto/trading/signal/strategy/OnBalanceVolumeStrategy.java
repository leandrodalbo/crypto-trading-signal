package com.crypto.trading.signal.strategy;

import com.crypto.trading.signal.indicator.BollingerBands;
import com.crypto.trading.signal.indicator.OnBalanceVolume;
import com.crypto.trading.signal.model.TradingSignal;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OnBalanceVolumeStrategy {

    private final OnBalanceVolume indicator;

    public OnBalanceVolumeStrategy(OnBalanceVolume indicator) {
        this.indicator = indicator;
    }

    public TradingSignal obvSignal(float[] values, float[] volumes) {

        Map<String, double[]> obvMap = indicator.obv(values, volumes);
        double[] obv = obvMap.get(OnBalanceVolume.OBV_KEY);
        double[] obvma = obvMap.get(OnBalanceVolume.OBV_MA_KEY);

        if (obv[obv.length - 5] <= obvma[obvma.length - 5] && obv[obv.length - 1] > obvma[obvma.length - 1]) {
            return TradingSignal.BUY;
        }

        if (obv[obv.length - 5] >= obvma[obvma.length - 5] && obv[obv.length - 1] < obvma[obvma.length - 1]) {
            return TradingSignal.SELL;
        }

        return TradingSignal.NONE;
    }
}
