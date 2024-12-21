package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class OneHourSignalService {

    private final OneHourRepository oneHourRepository;

    public OneHourSignalService(OneHourRepository oneHourRepository) {
        this.oneHourRepository = oneHourRepository;
    }

    public List<OneHour> getByStrength(TradingSignal signal, SignalStrength strength) {
        if (TradingSignal.SELL.equals(signal))
            return filterOutdated(this.oneHourRepository.findBySellStrength(strength));
        return filterOutdated(this.oneHourRepository.findByBuyStrength(strength));
    }

    public List<OneHour> filterOutdated(List<OneHour> allSignals)
    {
        return allSignals
                .stream()
                .filter(it -> it.signalTime() > Instant.now().minus(Duration.ofHours(12)).toEpochMilli())
                .toList();
    }
}
