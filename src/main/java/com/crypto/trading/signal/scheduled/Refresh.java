package com.crypto.trading.signal.scheduled;


import com.crypto.trading.signal.service.OneDaySignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Refresh {

    @Autowired
    OneDaySignalService oneDaySignalService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void refresh() {
        oneDaySignalService.randomRefresh();
    }
}
