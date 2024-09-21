package com.crypto.trading.signal.errorhandler.exeptions;

import com.crypto.trading.signal.errorhandler.message.Message;

public class SymbolAlreadyExistsException extends Exception {
    public SymbolAlreadyExistsException() {
        super(Message.SYMBOL_ALREADY_EXISTS.getMessage());
    }
}
