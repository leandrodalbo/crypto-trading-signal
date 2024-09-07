package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/onehour")
public class OneHourSignalController {

    private final OneHourSignalService service;

    public OneHourSignalController(OneHourSignalService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<OneHour> all() {
        return service.getAll();
    }

    @GetMapping("/{symbol}")
    public Mono<OneHour> byId(@PathVariable String symbol) {
        return service.getById(symbol);
    }

    @PutMapping("/refresh/{symbol}")
    public void refreshSymbol(@PathVariable String symbol) {
        service.refresh(symbol);
    }
}
