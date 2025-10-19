@echo off
echo Quick Maven Clean...
mvn clean
rmdir /s /q target 2>nul
mvn compile -U
if %errorlevel% equ 0 (
    echo Ready to run: mvn spring-boot:run
) else (
    echo Fix compilation errors first!
)
pause