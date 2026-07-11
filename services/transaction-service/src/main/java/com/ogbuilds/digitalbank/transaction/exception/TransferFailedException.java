package com.ogbuilds.digitalbank.transaction.exception;

public class TransferFailedException extends RuntimeException {

    public TransferFailedException(String message) {
        super(message);
    }

}