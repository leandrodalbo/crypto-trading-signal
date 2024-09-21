package com.crypto.trading.signal.errorhandler.exeptions;

import com.crypto.trading.signal.errorhandler.message.Message;

public class InvalidSymbolException extends Exception {
    public InvalidSymbolException() {
        super(Message.INVALID_SYMBOL.getMessage());
    }
}
