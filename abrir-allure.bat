@echo off
echo [INFO] Abrindo relatório Allure...

rem Tenta abrir o relatório em porta específica
allure open target/allure-results -p 56565 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException\|Starting web server\|at io.qameta.allure.Commands"

if %ERRORLEVEL% NEQ 0 (
    echo [INFO] Porta 56565 em uso, tentando porta alternativa...
    allure open target/allure-results -p 8080 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException\|Starting web server\|at io.qameta.allure.Commands"
)

echo [INFO] Relatório Allure disponível. Verifique se o navegador abriu automaticamente.
echo [INFO] Se não abriu, acesse manualmente: http://localhost:56565 ou http://localhost:8080 