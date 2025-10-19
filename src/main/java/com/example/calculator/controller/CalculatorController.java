package com.example.calculator.controller;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResponse;
import com.example.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST контроллер для обработки запросов калькулятора
 * Предоставляет endpoint для вычисления математических выражений
 * Обрабатывает CORS запросы от клиентских приложений
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Разрешаем запросы с любого origin для разработки
public class CalculatorController {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);
    
    private final CalculatorService calculatorService;

    /**
     * Конструктор с внедрением зависимости сервиса
     * @param calculatorService сервис для вычислений
     */
    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * POST endpoint для вычисления математических выражений
     * @param request запрос с выражением для вычисления
     * @return ResponseEntity с результатом вычисления
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        logger.info("Received calculation request: {}", request.getExpression());
        
        CalculationResponse response = calculatorService.calculate(request.getExpression());
        
        logger.info("Sending calculation response: {}", response);
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint для проверки работы сервера
     * @return простое сообщение о статусе сервера
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Health check requested");
        return ResponseEntity.ok("Calculator backend is running!");
    }
}