package com.example.calculator.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Модель запроса для вычисления математического выражения
 * Содержит строковое представление математического выражения
 * Используется для десериализации JSON запроса от клиента
 */
public class CalculationRequest {
    
    private final String expression;

    /**
     * Конструктор для десериализации JSON
     * @param expression математическое выражение в строковом формате
     */
    @JsonCreator
    public CalculationRequest(@JsonProperty("expression") String expression) {
        this.expression = expression;
    }

    /**
     * Получить математическое выражение
     * @return строковое представление выражения
     */
    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "CalculationRequest{expression='" + expression + "'}";
    }
}
