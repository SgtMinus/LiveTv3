package com.live.tv.LiveTv.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message + " не существует в базе данных!!!");
    }
}
