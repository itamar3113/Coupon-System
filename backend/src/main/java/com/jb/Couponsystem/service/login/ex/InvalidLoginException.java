package com.jb.Couponsystem.service.login.ex;

public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super("wrong user name or password");
    }
}
