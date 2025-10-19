package com.example.calculator.exception;

import com.example.calculator.model.CalculationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CalculationResponse handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        logger.error("Invalid JSON in request: {}", ex.getMessage());
        // ИСПРАВЛЕНО: используем конструктор вместо статического метода
        return new CalculationResponse("", "Invalid JSON format in request");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CalculationResponse handleMissingParams(MissingServletRequestParameterException ex) {
        logger.error("Missing request parameter: {}", ex.getParameterName());
        // ИСПРАВЛЕНО: используем конструктор вместо статического метода
        return new CalculationResponse("", "Missing required parameter: " + ex.getParameterName());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CalculationResponse handleGenericException(Exception ex) {
        logger.error("Internal server error: {}", ex.getMessage(), ex);
        // ИСПРАВЛЕНО: используем конструктор вместо статического метода
        return new CalculationResponse("", "Internal server error: " + ex.getMessage());
    }
}