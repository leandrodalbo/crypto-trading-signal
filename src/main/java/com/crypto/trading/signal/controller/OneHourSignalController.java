package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/onehour")
public class OneHourSignalController {

    private final OneHourSignalService service;

    public OneHourSignalController(OneHourSignalService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<OneHour> findAll() {
        return service.getAll();
    }

}
