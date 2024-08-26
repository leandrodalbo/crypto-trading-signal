package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneHourRepository extends ReactiveCrudRepository<OneHour, String> {
}
