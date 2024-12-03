package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.FourHour;
import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.entity.OneHour;
import com.crypto.trading.signal.model.SignalStrength;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.FourHourSignalService;
import com.crypto.trading.signal.service.OneDaySignalService;
import com.crypto.trading.signal.service.OneHourSignalService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(SignalController.class)
public class SignalControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OneDaySignalService oneDaySignalService;

    @MockBean
    private OneHourSignalService oneHourSignalService;

    @MockBean
    private FourHourSignalService fourHourSignalService;


    @Test
    void shouldFailWithInvalidSignalAndStrengthValues() throws Exception {

        MockHttpServletResponse res = mvc.perform(get("/books/abc123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        res = mvc.perform(get("/signals/onehour")
                        .queryParam("signal", TradingSignal.BUY.name())
                        .queryParam("strength", "rubbish")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


        res = mvc.perform(get("/signals/oneday")
                        .queryParam("signal", TradingSignal.BUY.name())
                        .queryParam("strength", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldFindOneHourBySignalAndStrengthValues() throws Exception {
        given(oneHourSignalService.getByStrength(any(), any())).willReturn(List.of(new OneHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        MockHttpServletResponse res = mvc.perform(get("/signals/onehour")
                        .queryParam("signal", TradingSignal.BUY.name())
                        .queryParam("strength", ExposedSignalStrength.MEDIUM.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(oneHourSignalService, times(1)).getByStrength(any(), any());
    }

    @Test
    void shouldFindFourHourBySignalAndStrengthValues() throws Exception {
        given(fourHourSignalService.getByStrength(any(), any())).willReturn(List.of(new FourHour("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        MockHttpServletResponse res = mvc.perform(get("/signals/fourhour")
                        .queryParam("signal", TradingSignal.BUY.name())
                        .queryParam("strength", ExposedSignalStrength.STRONG.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(fourHourSignalService, times(1)).getByStrength(any(), any());
    }

    @Test
    void shouldFindOneDayBySignalAndStrengthValues() throws Exception {
        given(oneDaySignalService.getByStrength(any(), any())).willReturn(List.of(new OneDay("BTCUSDT", Instant.now().toEpochMilli(), SignalStrength.LOW, SignalStrength.LOW, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, TradingSignal.BUY, 0)));

        MockHttpServletResponse res = mvc.perform(get("/signals/oneday")
                        .queryParam("signal", TradingSignal.BUY.name())
                        .queryParam("strength", ExposedSignalStrength.STRONG.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(oneDaySignalService, times(1)).getByStrength(any(), any());
    }

}
