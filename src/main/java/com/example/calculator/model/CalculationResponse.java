package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationResponse {
    
    private Double result;
    private String expression;
    private String message;

    public CalculationResponse() {}

    // Конструктор для успеха
    public CalculationResponse(Double result, String expression, String message) {
        this.result = result;
        this.expression = expression;
        this.message = message;
    }

    // Конструктор для ошибки
    public CalculationResponse(String expression, String message) {
        this.result = null;
        this.expression = expression;
        this.message = message;
    }

    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }
    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}