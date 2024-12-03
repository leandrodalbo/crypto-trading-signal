package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneHourRepository extends CrudRepository<OneHour, String> {
    List<OneHour> findByBuyStrength(SignalStrength buyStrength);
    List<OneHour> findBySellStrength(SignalStrength sellStrength);
}
