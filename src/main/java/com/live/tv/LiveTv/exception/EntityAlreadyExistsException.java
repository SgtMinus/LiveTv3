package com.live.tv.LiveTv.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message + " уже существует в базе данных!!!");
    }
}
