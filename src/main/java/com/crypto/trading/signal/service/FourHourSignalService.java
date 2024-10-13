package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.FourHourRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@Service
public class FourHourSignalService {

    private final FourHourRepository fourHourRepository;

    public FourHourSignalService(FourHourRepository fourHourRepository) {
        this.fourHourRepository = fourHourRepository;
    }

    public Flux<FourHour> getAll() {
        return fourHourRepository.findAll();
    }

    public Flux<FourHour> getByStrength(TradingSignal signal, SignalStrength strength) {

        if (TradingSignal.SELL.equals(signal))
            return this.fourHourRepository.findBySellStrength(strength)
                    .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(4))));

        return this.fourHourRepository.findByBuyStrength(strength)
                .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(4))));
    }
}
