@echo off
setlocal EnableDelayedExpansion

rem Caminho do arquivo de propriedades
set PROP_FILE=src\test\resources\allure.properties
set ENABLED=

rem Lê a propriedade allure.enabled
for /f "usebackq tokens=1,2 delims== " %%a in ("%PROP_FILE%") do (
    if /i "%%a"=="allure.enabled" set ENABLED=%%b
)

rem Se não encontrou a propriedade, assume false (Allure desativado por padrão para IDE)
if "%ENABLED%"=="" set ENABLED=false

if /i "%ENABLED%"=="true" (
    echo [INFO] Allure ativado para IDE. Executando com perfil with-allure...
    mvn test -P with-allure
) else (
    echo [INFO] Allure desativado para IDE. Executando com perfil no-allure...
    mvn test -P no-allure
)

endlocal
exit /b 