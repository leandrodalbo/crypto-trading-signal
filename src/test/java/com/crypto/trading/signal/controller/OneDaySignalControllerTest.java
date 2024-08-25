package com.crypto.trading.signal.controller;

import com.crypto.trading.signal.entity.OneDay;
import com.crypto.trading.signal.model.TradingSignal;
import com.crypto.trading.signal.service.OneDaySignalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(OneDaySignalController.class)
public class OneDaySignalControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private OneDaySignalService service;


    @Test
    void shouldGetAllBooks() throws Exception {
        given(service.getAll()).willReturn(List.of(new OneDay("BTCUSDT", TradingSignal.BUY, 0)));

        MockHttpServletResponse res = mvc.perform(get("/oneday/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(service, times(1)).getAll();
    }
}
