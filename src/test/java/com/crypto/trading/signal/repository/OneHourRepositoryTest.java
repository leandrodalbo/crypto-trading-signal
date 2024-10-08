package com.crypto.trading.signal.repository;

import com.crypto.trading.signal.model.SignalStrength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Testcontainers
public class OneHourRepositoryTest {

    @Container
    static PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    private OneHourRepository repository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", OneHourRepositoryTest::postgresUrl);
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);

    }

    private static String postgresUrl() {
        return String.format("r2dbc:postgresql://%s:%s/%s",
                container.getContainerIpAddress(),
                container.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                container.getDatabaseName());
    }

    @Test
    void willFindById() {
        StepVerifier.create(repository.findById("BTCUSDT"))
                .expectNextMatches(itr -> itr.symbol().equals("BTCUSDT"))
                .verifyComplete();
    }

    @Test
    void willFindAll() {
        StepVerifier.create(repository.findAll())
                .thenConsumeWhile(it -> it.symbol() != null)
                .verifyComplete();
    }

    @Test
    void willFindByBuyStrength() {
        StepVerifier.create(repository.findByBuyStrength(SignalStrength.MEDIUM))
                .thenConsumeWhile(it -> SignalStrength.MEDIUM.equals(it.buyStrength()))
                .verifyComplete();
    }

    @Test
    void willFindBySellStrength() {
        StepVerifier.create(repository.findBySellStrength(SignalStrength.STRONG))
                .thenConsumeWhile(it -> SignalStrength.STRONG.equals(it.sellStrength()))
                .verifyComplete();
    }
}