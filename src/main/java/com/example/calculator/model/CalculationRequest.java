package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculationRequest {
    
    private final String expression;

    @JsonCreator
    public CalculationRequest(@JsonProperty("expression") String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "CalculationRequest{expression='" + expression + "'}";
    }
}