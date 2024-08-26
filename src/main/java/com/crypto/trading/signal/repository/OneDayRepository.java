package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.OneDay;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneDayRepository extends ReactiveCrudRepository<OneDay, String> {
}
