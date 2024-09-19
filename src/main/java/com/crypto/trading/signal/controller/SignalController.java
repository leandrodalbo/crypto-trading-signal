package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.service.FourHourSignalService;
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
    private final FourHourSignalService fourHourSignalService;

    public SignalController(OneDaySignalService daySignalService, OneHourSignalService hourSignalService, FourHourSignalService fourHourSignalService) {
        this.daySignalService = daySignalService;
        this.hourSignalService = hourSignalService;
        this.fourHourSignalService = fourHourSignalService;
    }

    @GetMapping("/oneday/all")
    public Flux<OneDay> findAllOneDay() {
        return daySignalService.getAll();
    }

    @GetMapping("/onehour/all")
    public Flux<OneHour> findAllOneHour() {
        return hourSignalService.getAll();
    }

    @GetMapping("/fourhour/all")
    public Flux<FourHour> findAllFourHour() {
        return fourHourSignalService.getAll();
    }
}
