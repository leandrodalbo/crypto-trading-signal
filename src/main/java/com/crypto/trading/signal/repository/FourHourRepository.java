package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.FourHour;

import com.crypto.trading.signal.model.SignalStrength;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FourHourRepository extends CrudRepository<FourHour, String> {
    List<FourHour> findByBuyStrength(SignalStrength buyStrength);
    List<FourHour> findBySellStrength(SignalStrength sellStrength);
}
