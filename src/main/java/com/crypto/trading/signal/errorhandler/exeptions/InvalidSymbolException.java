package com.crypto.trading.signal.errorhandler.exeptions;

import com.crypto.trading.signal.message.Message;

public class InvalidSymbolException extends Exception {
    public InvalidSymbolException() {
        super(Message.INVALID_SYMBOL.getMessage());
    }
}
