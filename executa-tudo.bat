@echo off
setlocal EnableDelayedExpansion

rem Caminho do arquivo de propriedades
set PROP_FILE=src\test\resources\allure.properties
set ENABLED=

rem Lê a propriedade allure.enabled
for /f "usebackq tokens=1,2 delims== " %%a in ("%PROP_FILE%") do (
    if /i "%%a"=="allure.enabled" set ENABLED=%%b
)

rem Se não encontrou a propriedade, assume true (Allure ativado por padrão)
if "%ENABLED%"=="" set ENABLED=true

if /i "%ENABLED%"=="true" (
    echo [INFO] Allure ativado. Executando testes e relatório...
    mvn test -P with-allure
    mvn allure:report
    echo [INFO] Tentando abrir relatório Allure...
    mvn allure:serve 2>&1 | findstr /v "Address already in use\|Could not serve the report\|java.net.BindException"
) else (
    echo [INFO] Allure desativado em allure.properties. Executando apenas os testes...
    mvn test -P no-allure
)

endlocal
exit /b 