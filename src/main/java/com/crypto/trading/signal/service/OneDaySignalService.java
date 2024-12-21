package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


@Service
public class OneDaySignalService {

    private final OneDayRepository oneDayRepository;

    public OneDaySignalService(OneDayRepository oneDayRepository) {
        this.oneDayRepository = oneDayRepository;
    }

    public List<OneDay> getByStrength(TradingSignal signal, SignalStrength strength) {
        if (TradingSignal.SELL.equals(signal))
            return filterOutdated(this.oneDayRepository.findBySellStrength(strength));
        return filterOutdated(this.oneDayRepository.findByBuyStrength(strength));
    }

    public List<OneDay> filterOutdated(List<OneDay> allSignals)
    {
        return allSignals
                .stream()
                .filter(it -> it.signalTime() > Instant.now().minus(Duration.ofDays(3)).toEpochMilli())
                .toList();
    }
}
