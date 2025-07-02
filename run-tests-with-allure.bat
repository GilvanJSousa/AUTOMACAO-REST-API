@echo off
echo ========================================
echo    Executando Testes com Allure
echo ========================================
echo.

echo 1. Limpando projeto...
call mvn clean

echo.
echo 2. Executando testes...
call mvn test

echo.
echo 3. Gerando relatorio Allure...
call mvn allure:report

echo.
echo 4. Abrindo relatorio no navegador...
call mvn allure:serve

echo.
echo ========================================
echo    Processo concluido!
echo ========================================
pause 