package com.infosystem.springmvc.exception;

public class MyBusinessException extends Exception {

    public MyBusinessException(){
        System.out.println("DEDUSHKA");
    }

    public MyBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyBusinessException(String message) {
        super(message);
    }

    public MyBusinessException(Throwable cause) {
        super(cause);
    }
}
