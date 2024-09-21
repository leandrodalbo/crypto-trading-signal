package com.crypto.trading.signal.service;

import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.repository.OneHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OneHourSignalServiceTest {

    @InjectMocks
    OneHourSignalService service;

    @Mock
    OneHourRepository oneHourRepository;

    @Test
    void shouldFindAllSymbols() {
        when(oneHourRepository.findAll()).thenReturn(Flux.just(new OneHour("BTCUSDT", TradingSignal.BUY, 0)));

        Flux<OneHour> result = service.getAll();

        StepVerifier.create(result)
                .thenConsumeWhile(it -> !it.symbol().isEmpty());

        verify(oneHourRepository, times(1)).findAll();
    }
}
