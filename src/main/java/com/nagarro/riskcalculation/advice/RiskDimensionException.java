package com.nagarro.riskcalculation.advice;

public class RiskDimensionException extends RuntimeException{
    public RiskDimensionException(String message) {
        super(message);
    }

    public RiskDimensionException(Throwable cause) {
        super(cause);
    }
}
