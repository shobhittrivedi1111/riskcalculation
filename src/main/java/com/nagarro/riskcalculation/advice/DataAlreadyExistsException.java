package com.nagarro.riskcalculation.advice;

public class DataAlreadyExistsException extends RuntimeException{
    public DataAlreadyExistsException(String message) {
        super(message);
    }

    public DataAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
