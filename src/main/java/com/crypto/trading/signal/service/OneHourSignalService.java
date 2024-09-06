package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OneHourSignalService {

    private final OneHourRepository repository;

    public OneHourSignalService(OneHourRepository repository) {
        this.repository = repository;
    }

    public Mono<OneHour> getById(String symbol) {
        return repository.findById(symbol);
    }

    public Flux<OneHour> getAll() {
        return repository.findAll();
    }
}
