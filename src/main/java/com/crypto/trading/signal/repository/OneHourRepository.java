package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface OneHourRepository extends ReactiveCrudRepository<OneHour, String> {
    Flux<OneHour> findByBuyStrength(SignalStrength buyStrength);

    Flux<OneHour> findBySellStrength(SignalStrength sellStrength);
}
