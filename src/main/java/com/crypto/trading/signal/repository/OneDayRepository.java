package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.SignalStrength;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OneDayRepository extends CrudRepository<OneDay, String> {
    List<OneDay> findByBuyStrength(SignalStrength buyStrength);
    List<OneDay> findBySellStrength(SignalStrength sellStrength);
}
