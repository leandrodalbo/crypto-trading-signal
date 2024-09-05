package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.service.OneDaySignalService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping("/{symbol}")
    public Mono<OneDay> byId(@PathVariable String symbol) {
        return service.getById(symbol);
    }

    @PostMapping("/add/{symbol}")
    public Mono<Object> add(@PathVariable String symbol) {
        return service.saveNewSymbol(symbol);
    }

    @DeleteMapping("/delete/{symbol}")
    public Mono<Object> delete(@PathVariable String symbol) {
        return service.deleteSymbol(symbol);
    }

    @PutMapping("/refresh/{symbol}")
    public Mono<Object> refresh(@PathVariable String symbol) {
        return service.refresh(symbol);
    }
}
