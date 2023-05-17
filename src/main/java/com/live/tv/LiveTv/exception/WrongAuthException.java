package com.live.tv.LiveTv.exception;

public class WrongAuthException extends RuntimeException {
    public WrongAuthException() {
        super("Неверные логин и/или пароль!!!");
    }
}
