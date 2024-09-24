package com.crypto.trading.signal.service;

import com.crypto.trading.signal.conf.RabbitConf;
import com.crypto.trading.signal.model.Signal;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
public class ConsumeSignalService {

    @RabbitListener(queues = RabbitConf.QUEUE_NAME)
    public void consumeSignal(final Signal signal) {
        System.out.println(signal);
    }
}
