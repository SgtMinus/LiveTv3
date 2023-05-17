package com.live.tv.LiveTv.exception;

public class InterestException extends RuntimeException {
    public InterestException() {
        super("Неверное значение интереса! Значение должно быть равно = [-1;1]");
    }
}
