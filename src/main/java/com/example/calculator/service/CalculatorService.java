package com.example.calculator.service;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.model.CalculationResponse;
import com.example.calculator.repository.CalculationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    private final CalculationHistoryRepository historyRepository;

    @Autowired
    public CalculatorService(CalculationHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public CalculationResponse calculate(String expression) {
        try {
            if (expression == null || expression.trim().isEmpty()) {
                return saveErrorToDatabase(expression, "Expression cannot be empty");
            }

            String normalized = expression.replace('×', '*').replace('÷', '/').replaceAll("\\s+", "");
            
            double result = evaluateWithPriority(normalized);
            return saveSuccessToDatabase(expression, result);

        } catch (ArithmeticException e) {
            return saveErrorToDatabase(expression, "Arithmetic error: " + e.getMessage());
        } catch (Exception e) {
            return saveErrorToDatabase(expression, "Calculation error: " + e.getMessage());
        }
    }

    private CalculationResponse saveSuccessToDatabase(String expression, double result) {
        CalculationHistory history = new CalculationHistory(expression, result, true, null);
        historyRepository.save(history);
        return createSuccessResponse(expression, result);
    }

    private CalculationResponse saveErrorToDatabase(String expression, String errorMessage) {
        CalculationHistory history = new CalculationHistory(expression, null, false, errorMessage);
        historyRepository.save(history);
        return createErrorResponse(expression, errorMessage);
    }

    public List<CalculationResponse> getCalculationHistory() {
        List<CalculationHistory> historyList = historyRepository.findAllByOrderByCreatedAtDesc();
        return convertToResponseList(historyList);
    }

    public List<CalculationResponse> getSuccessfulCalculations() {
        List<CalculationHistory> historyList = historyRepository.findBySuccessTrueOrderByCreatedAtDesc();
        return convertToResponseList(historyList);
    }

    public List<CalculationResponse> getFailedCalculations() {
        List<CalculationHistory> historyList = historyRepository.findBySuccessFalseOrderByCreatedAtDesc();
        return convertToResponseList(historyList);
    }

    private List<CalculationResponse> convertToResponseList(List<CalculationHistory> historyList) {
        List<CalculationResponse> responses = new ArrayList<>();
        for (CalculationHistory history : historyList) {
            responses.add(convertToResponse(history));
        }
        return responses;
    }

    private CalculationResponse convertToResponse(CalculationHistory history) {
        CalculationResponse response = new CalculationResponse();
        response.setExpression(history.getExpression());
        response.setResult(history.getResult());
        
        if (history.getSuccess()) {
            response.setMessage("Calculation successful");
        } else {
            response.setMessage(history.getErrorMessage());
        }
        
        return response;
    }

    private CalculationResponse createSuccessResponse(String expression, Double result) {
        CalculationResponse response = new CalculationResponse();
        response.setExpression(expression);
        response.setResult(result);
        response.setMessage("Calculation successful");
        return response;
    }

    private CalculationResponse createErrorResponse(String expression, String message) {
        CalculationResponse response = new CalculationResponse();
        response.setExpression(expression);
        response.setResult(null);
        response.setMessage(message);
        return response;
    }

    private double evaluateWithPriority(String expr) {
        expr = processMultiplicationAndDivision(expr);
        return processAdditionAndSubtraction(expr);
    }

    private String processMultiplicationAndDivision(String expr) {
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
            
            expr = expr.substring(0, matcher.start()) + result + expr.substring(matcher.end());
            matcher = pattern.matcher(expr);
        }
        
        return expr;
    }

    private double processAdditionAndSubtraction(String expr) {
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