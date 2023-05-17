package com.live.tv.LiveTv.exception;

public class JwtTokenException extends RuntimeException {
    public JwtTokenException() {
        super("Токен недействителен/невалиден!!!");
    }
}
