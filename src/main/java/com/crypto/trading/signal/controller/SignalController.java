package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.FourHourSignalService;
import com.crypto.trading.signal.service.OneDaySignalService;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


    @GetMapping("/oneday")
    public List<OneDay> findOneDayBySignalStrength(@RequestParam TradingSignal signal,
                                                   @RequestParam ExposedSignalStrength strength) {
        return daySignalService.getByStrength(signal, SignalStrength.fromExposedSignalStrength(strength));
    }


    @GetMapping("/onehour")
    public List<OneHour> findOneHourBySignalStrength(@RequestParam TradingSignal signal,
                                                     @RequestParam ExposedSignalStrength strength) {
        return hourSignalService.getByStrength(signal, SignalStrength.fromExposedSignalStrength(strength));
    }

    @GetMapping("/fourhour")
    public List<FourHour> findFourHourBySignalStrength(@RequestParam TradingSignal signal,
                                                       @RequestParam ExposedSignalStrength strength) {
        return fourHourSignalService.getByStrength(signal, SignalStrength.fromExposedSignalStrength(strength));
    }
}
