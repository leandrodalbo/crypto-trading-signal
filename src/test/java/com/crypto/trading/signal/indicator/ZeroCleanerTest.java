package com.crypto.trading.signal.indicator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZeroCleanerTest {

    ZeroCleaner cleaner = new ZeroCleaner();

    @Test
    void shouldRemoveZeros() {
        assertThat(cleaner.cleanUp(new double[]{0.0})).isEqualTo(new double[0]);
        assertThat(cleaner.cleanUp(new double[]{1.0f, 0.0f})).isEqualTo(new double[]{1.0f});
        assertThat(cleaner.cleanUp(new double[]{1.0f, 2.0f, 0.0f, 0.0f})).isEqualTo(new double[]{1.0f, 2.0f});
    }
}
