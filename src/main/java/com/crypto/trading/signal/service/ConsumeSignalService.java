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
            logSellOpportunity(signal);
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
        logger.info("Possible BUY opportunity...");
        printSignal(s);
    }

    private void logSellOpportunity(Signal s) {
        logger.info("Possible SELL opportunity...");
        printSignal(s);
    }

    private void printSignal(Signal s) {
        StringBuffer buffer = new StringBuffer("Indicators:\n");

        buffer.append("\t[Bollinger Bands =" + s.bollingerBands() + "]\n");
        buffer.append("\t[EMA =" + s.ema() + "]\n");
        buffer.append("\t[SMA =" + s.sma() + "]\n");
        buffer.append("\t[MACD =" + s.macd() + "]\n");
        buffer.append("\t[LINDA MACD =" + s.lindaMACD() + "]\n");
        buffer.append("\t[RSI =" + s.rsi() + "]\n");
        buffer.append("\t[RSI DIVERGENCE =" + s.rsiDivergence() + "]\n");
        buffer.append("\t[STOCHASTIC =" + s.stochastic() + "]\n");
        buffer.append("\t[OBV =" + s.obv() + "]\n");
        buffer.append("\t[ENGULFING CANDLES =" + s.engulfingCandle() + "]\n");
        buffer.append("\t[HAMMER/SHOOTING START CANDLES =" + s.hammerAndShootingStars() + "]\n");
        buffer.append("\t[TURTLE STRATEGY =" + s.turtleSignal() + "]\n");

        logger.info(String.format("Symbol: %s, Timeframe: %s, %s", s.symbol(), s.timeframe(), buffer.toString()));
    }
}
