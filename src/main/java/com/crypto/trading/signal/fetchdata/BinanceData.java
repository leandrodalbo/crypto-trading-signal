package com.crypto.trading.signal.fetchdata;

import com.crypto.trading.signal.model.Candle;
import com.crypto.trading.signal.model.Timeframe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class BinanceData {

    private static final String KLINES = "/api/v3/klines";
    private static final int TOTAL_CANDLES = 150;

    protected final WebClient client;

    private final Logger logger = LoggerFactory.getLogger(BinanceData.class);

    public BinanceData(WebClient webClient) {
        this.client = webClient;
    }

    public static Candle[] toCandlesArray(List values) {
        if (values == null || values.isEmpty()) {
            return new Candle[0];
        }

        Candle[] result = new Candle[values.size()];

        for (int i = 0; i < values.size(); i++) {
            List candle = (List) values.get(i);

            result[i] = Candle.of(
                    Float.parseFloat((String) candle.get(1)),
                    Float.parseFloat((String) candle.get(2)),
                    Float.parseFloat((String) candle.get(3)),
                    Float.parseFloat((String) candle.get(4)),
                    Integer.parseInt((String) candle.get(5))
            );

        }

        return result;
    }

    public Mono<Candle[]> fetchOHLC(String symbol, Timeframe speed) {
        return client.get()
                .uri(
                        builder -> builder.path(KLINES)
                                .queryParam("symbol", symbol)
                                .queryParam("interval", interval(speed))
                                .queryParam("limit", TOTAL_CANDLES)
                                .build())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Object.class)
                .map(it -> {
                    if (it instanceof List<?>)
                        return toCandlesArray((List) it);
                    return new Candle[0];
                })
                .doOnError(e -> logger.error(e.getClass().getSimpleName()));
    }

    private String interval(Timeframe speed) {
        return switch (speed) {
            case H1 -> "1h";
            case H4 -> "4h";
            default -> "1d";
        };
    }

}
