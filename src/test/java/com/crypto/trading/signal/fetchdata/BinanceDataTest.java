package com.crypto.trading.signal.fetchdata;

import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BinanceDataTest {

    ObjectMapper mapper = new ObjectMapper();

    private MockWebServer mockWebServer;

    private BinanceData binanceData;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        var webClient = WebClient.builder()
                .baseUrl(String.format("http://localhost:%s",
                        mockWebServer.getPort()))
                .build();


        this.binanceData = new BinanceData(webClient);
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }


    @Test
    void shouldFetchOHLCCandles() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(List.of(List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        ),
                        List.of(
                                1591258320000L,
                                "9640.7",
                                "9642.4",
                                "9640.6",
                                "9642.0",
                                "206",
                                1591258379999L,
                                "2.13660389",
                                48,
                                "119",
                                "1.23424865",
                                "0"
                        )

                )));

        mockWebServer.enqueue(mockResponse);

        Mono candles = binanceData.fetchOHLC("BTCUSD", Timeframe.H1);

        StepVerifier.create(candles)
                .expectNextMatches(it -> ((Candle[]) it).length == 3)
                .verifyComplete();

    }

    @Test
    void shouldHandleAndInvalidSymbol() throws JsonProcessingException {
        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\"code\":-1121,\"msg\":\"Invalid symbol.\"}");

        mockWebServer.enqueue(mockResponse);

        Mono candles = binanceData.fetchOHLC("BTCUSD", Timeframe.H1);

        StepVerifier.create(candles)
                .expectNextMatches(it -> ((Candle[]) it).length == 0)
                .verifyComplete();

    }

    @Test
    void willReturnAnArrayOfThreeCandles() {
        List<Object> c0 = List.of(
                String.valueOf(Instant.now().getEpochSecond()),
                "23.0",
                "24.0",
                "25.0",
                "26.0",
                "232"
        );

        List<Object> c1 = List.of(
                String.valueOf(Instant.now().getEpochSecond()),
                "27.0",
                "28.0",
                "29.0",
                "30.0",
                "232"
        );
        assertThat(BinanceData.toCandlesArray(List.of(c0, c1)))
                .isEqualTo(
                        new Candle[]{
                                Candle.of(23.0f,
                                        24.0f,
                                        25.0f,
                                        26.0f, 232),
                                Candle.of(27.0f,
                                        28.0f,
                                        29.0f,
                                        30.0f, 232)
                        }
                );
    }

    @Test
    void willReturnAndArrayOfNullValues() {
        assertThat(BinanceData.toCandlesArray(null)).isEqualTo(new Candle[0]);
        assertThat(BinanceData.toCandlesArray(List.of())).isEqualTo(new Candle[0]);
    }


}
