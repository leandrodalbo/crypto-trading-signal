package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;


@Service
public class OneDaySignalService {

    private final OneDayRepository oneDayRepository;

    public OneDaySignalService(OneDayRepository oneDayRepository) {
        this.oneDayRepository = oneDayRepository;
    }

    public Flux<OneDay> getByStrength(TradingSignal signal, SignalStrength strength) {

        if (TradingSignal.SELL.equals(signal))
            return this.oneDayRepository.findBySellStrength(strength)
                    .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(48))));

        return this.oneDayRepository.findByBuyStrength(strength)
                .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(48))));
    }
}
