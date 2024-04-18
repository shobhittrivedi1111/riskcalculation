package com.nagarro.riskcalculation.advice;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import java.rmi.ServerException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, Exception e) {
        log.error(status.getReasonPhrase(), e);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("errorCode", status.value());
        responseMap.put("errorMessage", status.getReasonPhrase());
        responseMap.put("errorDetails", e.getMessage());
        responseMap.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(responseMap, status);
    }
    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException e) {
        return createErrorResponse(HttpStatus.NOT_FOUND, e);
    }
    @ExceptionHandler({FormulaEvaluationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String,Object>> handleFormulaEvaluationException(FormulaEvaluationException e){
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }
    @ExceptionHandler({DataAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String,Object>> handleDataAlreadyExistsException(DataAlreadyExistsException e){
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }
    @ExceptionHandler({InvalidRangeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,Object>> handleInvalidRangeException(InvalidRangeException e){
        return createErrorResponse(HttpStatus.BAD_REQUEST,e);
    }
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(IllegalArgumentException e){
        return createErrorResponse(HttpStatus.BAD_REQUEST,e);
    }
    @ExceptionHandler({EntityExistsException.class, SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,Object>> handleEntityExistsException(EntityExistsException e){
        return createErrorResponse(HttpStatus.BAD_REQUEST,e);
    }
}

