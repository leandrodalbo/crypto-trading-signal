package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OneDayRepository extends ReactiveCrudRepository<OneDay, String> {
    Flux<OneDay> findByBuyStrength(SignalStrength buyStrength);
    Flux<OneDay> findBySellStrength(SignalStrength sellStrength);
}
