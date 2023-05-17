package com.live.tv.LiveTv.exception;

public class ReputationException extends RuntimeException {
    public ReputationException() {
        super("У Вас не может быть репутация меньше, чем у оцениваемого вами пользователя.");
    }
}
