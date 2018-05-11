package com.infosystem.springmvc.exception;

public class LogicException extends MyBusinessException {

    public LogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogicException(String message) {
        super(message);
    }

    public LogicException(Throwable cause) {
        super(cause);
    }
}