@echo off
setlocal enabledelayedexpansion

REM Lê o token salvo pelo login
set /p TOKEN=<token.txt

echo Token lido: %TOKEN%
echo.

curl -X POST http://localhost:3000/transferencias ^
-H "Authorization: Bearer %TOKEN%" ^
-H "Content-Type: application/json" ^
-d "{\"contaOrigem\": \"6866ef0c822da5a2bb628767\", \"contaDestino\": \"6866ef0c822da5a2bb628768\", \"valor\": 50.00, \"token\": \"%TOKEN%\"}"

echo.
echo Transferencia concluida!
pause 