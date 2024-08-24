package com.crypto.trading.signal.repository;


import com.crypto.trading.signal.entity.OneDay;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class OneDayRepositoryTest {

    @Autowired
    private OneDayRepository repository;

    @Test
    void willFindById() {

        Optional<OneDay> oneDay = repository.findById("BTCUSDT");

        assertThat(oneDay.isPresent()).isTrue();

    }


}