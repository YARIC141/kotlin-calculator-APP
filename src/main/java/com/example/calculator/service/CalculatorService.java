package com.example.calculator.service;

import com.example.calculator.model.CalculationResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculationResponse calculate(String expression) {
        try {
            if (expression == null || expression.trim().isEmpty()) {
                return new CalculationResponse(expression, "Expression cannot be empty");
            }

            String normalized = expression.replace('×', '*').replace('÷', '/').replaceAll("\\s+", "");
            
            // Используем улучшенный калькулятор с приоритетом операций
            double result = evaluateWithPriority(normalized);
            return new CalculationResponse(result, expression, "Calculation successful");

        } catch (ArithmeticException e) {
            return new CalculationResponse(expression, "Arithmetic error: " + e.getMessage());
        } catch (Exception e) {
            return new CalculationResponse(expression, "Calculation error: " + e.getMessage());
        }
    }

    private double evaluateWithPriority(String expr) {
        // Сначала обрабатываем умножение и деление
        expr = processMultiplicationAndDivision(expr);
        // Затем сложение и вычитание
        return processAdditionAndSubtraction(expr);
    }

    private String processMultiplicationAndDivision(String expr) {
        // Регулярное выражение для поиска чисел и операторов * /
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+\\.?\\d*)([*/])(\\d+\\.?\\d*)");
        java.util.regex.Matcher matcher = pattern.matcher(expr);
        
        while (matcher.find()) {
            double left = Double.parseDouble(matcher.group(1));
            String operator = matcher.group(2);
            double right = Double.parseDouble(matcher.group(3));
            
            double result;
            if (operator.equals("*")) {
                result = left * right;
            } else {
                if (right == 0) throw new ArithmeticException("Division by zero");
                result = left / right;
            }
            
            // Заменяем выражение на результат
            expr = expr.substring(0, matcher.start()) + result + expr.substring(matcher.end());
            matcher = pattern.matcher(expr);
        }
        
        return expr;
    }

    private double processAdditionAndSubtraction(String expr) {
        // Регулярное выражение для сложения и вычитания
        String[] parts = expr.split("(?=[+-])|(?<=[+-])");
        double result = Double.parseDouble(parts[0]);
        
        for (int i = 1; i < parts.length; i += 2) {
            String operator = parts[i];
            double value = Double.parseDouble(parts[i + 1]);
            
            if (operator.equals("+")) {
                result += value;
            } else {
                result -= value;
            }
        }
        
        return result;
    }
}