package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.repository.FourHourRepository;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FourHourSignalService {

    private final FourHourRepository fourHourRepository;

    public FourHourSignalService(FourHourRepository fourHourRepository) {
        this.fourHourRepository = fourHourRepository;
    }

    public Flux<FourHour> getAll() {
        return fourHourRepository.findAll();
    }
}
