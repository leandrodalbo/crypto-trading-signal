package com.crypto.trading.signal.service.adapter;

import com.crypto.trading.signal.model.Candle;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class AdapterService {

    public float[] closingPrices(Mono<Candle[]> candles) {
        return toPrimitiveArray(Arrays.stream(candles.block()).map(Candle::close)
                .toList());
    }

    private float[] toPrimitiveArray(List<Float> items) {
        float[] result = new float[items.size()];
        for (int i = 0; i < result.length; i++)
            result[i] = items.get(i);
        return result;
    }
}
