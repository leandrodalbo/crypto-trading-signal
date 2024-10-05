package com.crypto.trading.signal.errorhandler;

import com.crypto.trading.signal.errorhandler.exeptions.InvalidSymbolException;
import com.crypto.trading.signal.errorhandler.exeptions.SymbolAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrors {

    @ExceptionHandler({InvalidSymbolException.class})
    public ResponseEntity<String> notAcceptable(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                e.getMessage());
    }

    @ExceptionHandler(SymbolAlreadyExistsException.class)
    public ResponseEntity<String> symbolExists(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

}
