CALCULATOR-BACKEND/
├── .vscode/
│   └── settings.json
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── calculator/
│       │               ├── controller/
│       │               │   └── CalculatorController.java
│       │               ├── exception/
│       │               │   └── GlobalExceptionHandler.java
│       │               ├── model/
│       │               │   ├── CalculationRequest.java
│       │               │   ├── CalculationResponse.java
│       │               │   └── CalculationHistory.java      ← НОВЫЙ ФАЙЛ
│       │               ├── repository/                      ← НОВАЯ ПАПКА
│       │               │   └── CalculationHistoryRepository.java
│       │               ├── service/
│       │               │   └── CalculatorService.java
│       │               └── CalculatorApplication.java
│       └── resources/
│           └── application.properties
├── target/
├── .gitignore
└── pom.xml

# Получить всю историю
curl -X GET http://localhost:8080/api/history

# Получить последние 5 вычислений
curl -X GET "http://localhost:8080/api/history/recent?limit=5"

# Поиск вычислений с числом 2
curl -X GET "http://localhost:8080/api/history/search?expression=2"

curl http://localhost:8081/db-info

curl -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d "{\"expression\": \"2*3+3*4\"}"