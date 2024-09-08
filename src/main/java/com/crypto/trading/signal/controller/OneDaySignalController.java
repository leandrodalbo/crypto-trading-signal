package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.service.OneDaySignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Flux<OneDay> findAll() {
        return service.getAll();
    }

    @GetMapping("/{symbol}")
    public Mono<OneDay> findById(@PathVariable String symbol) {
        return service.getById(symbol);
    }

    @PostMapping("/add/{symbol}")
    public void addSymbol(@PathVariable String symbol) throws Exception {
        service.saveNewSymbol(symbol);
    }

    @DeleteMapping("/delete/{symbol}")
    public void deleteSymbol(@PathVariable String symbol) throws Exception {
        service.deleteSymbol(symbol);
    }

    @PutMapping("/refresh/{symbol}")
    public void refreshSymbol(@PathVariable String symbol) throws Exception {
        service.refresh(symbol);
    }
}
