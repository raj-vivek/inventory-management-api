package com.ladyshopee.api.error;

public class LoginException extends RuntimeException{
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }
}
