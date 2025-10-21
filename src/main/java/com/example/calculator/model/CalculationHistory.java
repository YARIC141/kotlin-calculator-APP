package com.example.calculator.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calculation_history")
public class CalculationHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String expression;
    
    private Double result;
    
    @Column(nullable = false)
    private Boolean success;
    
    private String errorMessage;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    // Конструкторы
    public CalculationHistory() {}
    
    public CalculationHistory(String expression, Double result, Boolean success, String errorMessage) {
        this.expression = expression;
        this.result = result;
        this.success = success;
        this.errorMessage = errorMessage;
        this.createdAt = LocalDateTime.now();
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }
    
    public Double getResult() { return result; }
    public void setResult(Double result) { this.result = result; }
    
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @Override
    public String toString() {
        return "CalculationHistory{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", result=" + result +
                ", success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}