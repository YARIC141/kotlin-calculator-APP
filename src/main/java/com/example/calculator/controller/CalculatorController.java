package com.example.calculator.controller;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResponse;
import com.example.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        logger.info("Received calculation request: {}", request.getExpression());
        
        CalculationResponse response = calculatorService.calculate(request.getExpression());
        
        logger.info("Sending calculation response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<CalculationResponse>> getHistory() {
        logger.info("Requesting calculation history");
        List<CalculationResponse> history = calculatorService.getCalculationHistory();
        logger.info("Returning {} history entries", history.size());
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/successful")
    public ResponseEntity<List<CalculationResponse>> getSuccessfulHistory() {
        logger.info("Requesting successful calculation history");
        List<CalculationResponse> history = calculatorService.getSuccessfulCalculations();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/failed")
    public ResponseEntity<List<CalculationResponse>> getFailedHistory() {
        logger.info("Requesting failed calculation history");
        List<CalculationResponse> history = calculatorService.getFailedCalculations();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Health check requested");
        return ResponseEntity.ok("Calculator backend is running!");
    }
}