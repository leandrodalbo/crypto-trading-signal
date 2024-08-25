package com.crypto.trading.signal.scheduled;


import com.crypto.trading.signal.service.RefreshDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshingSymbols {

    @Autowired
    RefreshDataService refreshDataService;


    @Scheduled(cron = "* */3 * * * *")
    public void searchData() {
        refreshDataService.refreshOneDaySymbols();
    }
}
