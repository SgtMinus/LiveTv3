package com.live.tv.LiveTv.exception;

public class BannedUserException extends RuntimeException {
    public BannedUserException(String message) {
        super(message + " заблокирован на данный момент.");
    }
}
