package com.ogbuilds.digitalbank.auth.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message){
        super(message);
    }

}