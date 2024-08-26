package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.service.OneDaySignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/oneday")
public class OneDaySignalController {

    private final OneDaySignalService service;

    public OneDaySignalController(OneDaySignalService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<OneDay> all() {
        return service.getAll();
    }
}
