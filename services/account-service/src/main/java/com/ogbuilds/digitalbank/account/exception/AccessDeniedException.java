package com.ogbuilds.digitalbank.account.exception;

public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message)
    {
        super(message);
    }

}
