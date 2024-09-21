package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OneHourSignalService {

    private final OneHourRepository oneHourRepository;

    public OneHourSignalService(OneHourRepository oneHourRepository) {
        this.oneHourRepository = oneHourRepository;
    }

    public Flux<OneHour> getAll() {
        return oneHourRepository.findAll();
    }

}
