package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.model.SignalStrength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class OneDayRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.4"));

    @Autowired
    private OneDayRepository repository;

    @Test
    void willFindById() {
        var item = repository.findById("BTCUSDT");
        assertThat("BTCUSDT").isEqualTo(item.get().symbol());
    }

    @Test
    void willFindByBuyStrength() {
        assertThat(repository.findByBuyStrength(SignalStrength.LOW)
                .stream()
                .filter(it -> !SignalStrength.LOW.equals(it.buyStrength()))
                .toList()).isEmpty();
    }

    @Test
    void willFindBySellStrength() {
        assertThat(repository.findBySellStrength(SignalStrength.STRONG)
                .stream()
                .filter(it -> !SignalStrength.STRONG.equals(it.sellStrength()))
                .toList()).isEmpty();
    }
}