package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
public class OneDaySignalService {

    private final OneDayRepository oneDayRepository;

    public OneDaySignalService(OneDayRepository oneDayRepository) {
        this.oneDayRepository = oneDayRepository;
    }

    public Flux<OneDay> getAll() {
        return this.oneDayRepository.findAll();
    }

    public Flux<OneDay> getByStrength(TradingSignal signal, SignalStrength strength) {

        if (TradingSignal.SELL.equals(signal))
            return this.oneDayRepository.findBySellStrength(strength);

        return this.oneDayRepository.findByBuyStrength(strength);
    }
}
