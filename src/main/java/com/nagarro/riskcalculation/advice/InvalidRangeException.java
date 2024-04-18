package com.nagarro.riskcalculation.advice;

public class InvalidRangeException extends RuntimeException{
    public InvalidRangeException(String message) {
        super(message);
    }

    public InvalidRangeException(Throwable cause) {
        super(cause);
    }
}
