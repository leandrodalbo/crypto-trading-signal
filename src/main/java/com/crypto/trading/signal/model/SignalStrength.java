package com.crypto.trading.signal.model;

import com.crypto.trading.signal.controller.ExposedSignalStrength;

import java.util.Objects;

public enum SignalStrength {
    STRONG, MEDIUM, LOW;

    public static SignalStrength fromExposedSignalStrength(ExposedSignalStrength it){
        if (Objects.requireNonNull(it) == ExposedSignalStrength.STRONG) {
            return SignalStrength.STRONG;
        }
        return SignalStrength.MEDIUM;
    }
}
