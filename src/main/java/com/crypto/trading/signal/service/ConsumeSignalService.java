package com.crypto.trading.signal.service;

import com.crypto.trading.signal.conf.RabbitConf;
import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.Signal;
import com.crypto.trading.signal.model.SignalStrength;
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
        if (Timeframe.D1.equals(signal.timeframe()))
            saveOneDay(signal);

        if (Timeframe.H1.equals(signal.timeframe()))
            saveOneHour(signal);

        if (Timeframe.H4.equals(signal.timeframe()))
            saveFourHour(signal);

        if (SignalStrength.STRONG.equals(signal.buyStrength()) || SignalStrength.MEDIUM.equals(signal.buyStrength())) {
            logBuyOpportunity(signal);
        }
        if (SignalStrength.STRONG.equals(signal.sellStrength()) || SignalStrength.MEDIUM.equals(signal.sellStrength())) {
            logBuyOpportunity(signal);
        }
    }

    private void saveOneDay(Signal signal) {
        oneDayRepository.findById(signal.symbol())
                .defaultIfEmpty(OneDay.fromSignal(signal, null))
                .flatMap(oneDay ->
                        oneDayRepository.save(OneDay.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }

    private void saveOneHour(Signal signal) {
        oneHourRepository.findById(signal.symbol())
                .defaultIfEmpty(OneHour.fromSignal(signal, null))
                .flatMap(oneDay ->
                        oneHourRepository.save(OneHour.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }

    private void saveFourHour(Signal signal) {
        fourHourRepository.findById(signal.symbol())
                .defaultIfEmpty(FourHour.fromSignal(signal, null
                ))
                .flatMap(oneDay ->
                        fourHourRepository.save(FourHour.fromSignal(signal, oneDay.version()))
                ).subscribe();
    }

    private void logBuyOpportunity(Signal s) {
        logger.info("Possible buying opportunity...");
        printSignal(s);
    }

    private void logSellOpportunity(Signal s) {
        logger.info("Possible selling opportunity...");
        printSignal(s);
    }

    private void printSignal(Signal s) {
        logger.info(String.format("Symbol: %s", s.symbol()));
        logger.info(String.format("Timeframe: %s", s.timeframe()));
        logger.info("Indicators: \n" +
                "\t [Bollinger Bands = % \n" + s.bollingerBands() + "]" +
                "\t [EMA = % \n" + s.ema() + "]" +
                "\t [SMA = % \n" + s.sma() + "]" +
                "\t [MACD = % \n" + s.macd() + "]" +
                "\t [LINDA MACD = % \n" + s.lindaMACD() + "]" +
                "\t [RSI = % \n" + s.rsi() + "]" +
                "\t [RSI DIVERGENCE = % \n" + s.rsiDivergence() + "]" +
                "\t [STOCHASTIC = % \n" + s.stochastic() + "]" +
                "\t [OBV = % \n" + s.obv() + "]" +
                "\t [ENGULFING CANDLES = % \n" + s.engulfingCandle() + "]" +
                "\t [HAMMER/SHOOTING START CANDLES = % \n" + s.hammerAndShootingStars() + "]" +
                "\t [TURTLE STRATEGY = % \n" + s.turtleSignal() + "]"
        );
    }
}
