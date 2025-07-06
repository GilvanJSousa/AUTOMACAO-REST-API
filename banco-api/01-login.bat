@echo off
echo Obtendo novo token JWT...
echo.

curl -s -X POST http://localhost:3000/login -H "Content-Type: application/json" -d "{\"username\": \"admin\", \"senha\": \"admin123\"}" > login_response.txt

REM Extrai o token do JSON e salva em token.txt
for /f "tokens=2 delims=:," %%a in ('findstr /i "token" login_response.txt') do (
    set "TOKEN=%%~a"
)
set "TOKEN=%TOKEN:~1,-1%"
echo %TOKEN% > token.txt

echo Token salvo em token.txt: %TOKEN%
echo Teste concluido!
pause 