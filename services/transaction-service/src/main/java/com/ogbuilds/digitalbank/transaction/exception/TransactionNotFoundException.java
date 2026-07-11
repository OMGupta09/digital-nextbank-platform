package com.ogbuilds.digitalbank.transaction.exception;

public class TransactionNotFoundException extends RuntimeException{

    public TransactionNotFoundException(String message)
    {
        super(message);
    }

}
