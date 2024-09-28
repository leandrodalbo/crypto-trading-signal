package com.crypto.trading.signal.service;

import com.crypto.trading.signal.conf.RabbitConf;
import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.Timeframe;
import com.crypto.trading.signal.repository.FourHourRepository;
import com.crypto.trading.signal.repository.OneDayRepository;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
public class ConsumeSignalService {

    private final Logger logger = LoggerFactory.getLogger(ConsumeSignalService.class);

    private final OneHourRepository oneHourRepository;
    private final OneDayRepository oneDayRepository;
    private final FourHourRepository fourHourRepository;

    public ConsumeSignalService(OneHourRepository oneHourRepository, OneDayRepository oneDayRepository, FourHourRepository fourHourRepository) {
        this.oneHourRepository = oneHourRepository;
        this.oneDayRepository = oneDayRepository;
        this.fourHourRepository = fourHourRepository;
    }

    @RabbitListener(queues = RabbitConf.QUEUE_NAME)
    public void consumeSignal(final Signal signal) {
        logger.info(signal.toString());


        if (Timeframe.D1.equals(signal.timeframe()))
            saveOneDay(signal);

        if (Timeframe.H1.equals(signal.timeframe()))
            saveOneHour(signal);

        if (Timeframe.H4.equals(signal.timeframe()))
            saveFourHour(signal);
    }

    private void saveOneDay(Signal signal) {
        oneDayRepository.findById(signal.symbol())
                .defaultIfEmpty(OneDay.fromSignal(signal, 0))
                .flatMap(oneDay ->
                        oneDayRepository.save(OneDay.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }

    private void saveOneHour(Signal signal) {
        oneHourRepository.findById(signal.symbol())
                .defaultIfEmpty(OneHour.fromSignal(signal, 0))
                .flatMap(oneDay ->
                        oneHourRepository.save(OneHour.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }

    private void saveFourHour(Signal signal) {
        fourHourRepository.findById(signal.symbol())
                .defaultIfEmpty(FourHour.fromSignal(signal, 0))
                .flatMap(oneDay ->
                        fourHourRepository.save(FourHour.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }
}
