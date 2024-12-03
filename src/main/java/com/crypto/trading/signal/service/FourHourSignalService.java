package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.FourHourRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class FourHourSignalService {

    private final FourHourRepository fourHourRepository;

    public FourHourSignalService(FourHourRepository fourHourRepository) {
        this.fourHourRepository = fourHourRepository;
    }

    public List<FourHour> getByStrength(TradingSignal signal, SignalStrength strength) {

        if (TradingSignal.SELL.equals(signal))
            return this.fourHourRepository.findBySellStrength(strength)
                    .stream()
                    .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(24))))
                    .toList();

        return this.fourHourRepository.findByBuyStrength(strength)
                .stream()
                .filter(it -> Instant.ofEpochMilli(it.signalTime()).isAfter(Instant.now().minus(Duration.ofHours(24))))
                .toList();
    }
}
