package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.entity.OneDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneDayRepository extends CrudRepository<OneDay, String> {
}
