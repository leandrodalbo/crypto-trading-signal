package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.repository.OneDayRepository;
import org.springframework.stereotype.Service;

@Service
public class OneDaySignalService {

    private final OneDayRepository oneDayRepository;


    public OneDaySignalService(OneDayRepository oneDayRepository) {
        this.oneDayRepository = oneDayRepository;
    }

    public Iterable<OneDay> getAll() {
        return this.oneDayRepository.findAll();
    }
}
