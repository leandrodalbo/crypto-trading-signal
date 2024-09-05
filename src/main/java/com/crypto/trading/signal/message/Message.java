package com.crypto.trading.signal.message;

public enum Message {
    SYMBOL_ALREADY_EXISTS("Symbol Already Exists"),
    SYMBOL_NOT_EXISTS("Symbol Not Exists"),
    INVALID_SYMBOL("Invalid Symbol");

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
