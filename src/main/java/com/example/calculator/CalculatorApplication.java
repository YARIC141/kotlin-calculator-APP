package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс Spring Boot приложения
 * Запускает REST API сервер калькулятора
 * Автоматически настраивает компоненты Spring и запускает встроенный Tomcat сервер
 */
@SpringBootApplication
public class CalculatorApplication {

    /**
     * Точка входа в приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }
}
