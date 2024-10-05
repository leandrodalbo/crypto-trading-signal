package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.FourHourSignalService;
import com.crypto.trading.signal.service.OneDaySignalService;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/oneday")
    public Flux<OneDay> findOneDayBySignalStrength(@RequestParam TradingSignal signal,
                                                   @RequestParam SignalStrength strength) {
        return daySignalService.getByStrength(signal, strength);
    }

    @GetMapping("/onehour/all")
    public Flux<OneHour> findAllOneHour() {
        return hourSignalService.getAll();
    }

    @GetMapping("/onehour")
    public Flux<OneHour> findOneHourBySignalStrength(@RequestParam TradingSignal signal,
                                                     @RequestParam SignalStrength strength) {
        return hourSignalService.getByStrength(signal, strength);
    }

    @GetMapping("/fourhour/all")
    public Flux<FourHour> findAllFourHour() {
        return fourHourSignalService.getAll();
    }

    @GetMapping("/fourhour")
    public Flux<FourHour> findFourHourBySignalStrength(@RequestParam TradingSignal signal,
                                                       @RequestParam SignalStrength strength) {
        return fourHourSignalService.getByStrength(signal, strength);
    }
}
