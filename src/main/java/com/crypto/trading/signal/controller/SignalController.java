package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.service.OneDaySignalService;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/signals")
public class SignalController {

    private final OneDaySignalService daySignalService;
    private final OneHourSignalService hourSignalService;

    public SignalController(OneDaySignalService daySignalService, OneHourSignalService hourSignalService) {
        this.daySignalService = daySignalService;
        this.hourSignalService = hourSignalService;
    }

    @GetMapping("/oneday/all")
    public Flux<OneDay> findAllOneDay() {
        return daySignalService.getAll();
    }

    @GetMapping("/onehour/all")
    public Flux<OneHour> findAllOneHour() {
        return hourSignalService.getAll();
    }
}
