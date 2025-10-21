package com.example.calculator.repository;

import com.example.calculator.model.CalculationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationHistoryRepository extends JpaRepository<CalculationHistory, Long> {
    
    // Находим все записи, отсортированные по дате создания (новые сначала)
    List<CalculationHistory> findAllByOrderByCreatedAtDesc();
    
    // Находим успешные вычисления
    List<CalculationHistory> findBySuccessTrueOrderByCreatedAtDesc();
    
    // Находим неуспешные вычисления
    List<CalculationHistory> findBySuccessFalseOrderByCreatedAtDesc();
}