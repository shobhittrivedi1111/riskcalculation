package com.nagarro.riskcalculation.advice;

public class FormulaEvaluationException extends RuntimeException{
    public FormulaEvaluationException(String message) {
        super(message);
    }

    public FormulaEvaluationException(Throwable cause) {
        super(cause);
    }
}
