package com.jb.Couponsystem.rest.controller.ex;

public class TokenInvalidOrExpiredException extends Exception {

    public TokenInvalidOrExpiredException() {
        super("Invalid or expired token");
    }

    public TokenInvalidOrExpiredException(String message) {
        super(message);
    }
}
