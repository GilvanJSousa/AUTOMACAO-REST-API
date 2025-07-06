@echo off
echo Atualizando transferencia parcialmente...
echo.

curl -X PATCH http://localhost:3000/transferencias/6866c42554f7e3896d3a9f50 ^
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY4NjZiNzJkYmIzM2NjMzBmMDQxM2I3NCIsInVzZXJuYW1lIjoiYWRtaW4iLCJpYXQiOjE3NTE1NjUyMTEsImV4cCI6MTc1MTU2ODgxMX0.jit2bJu6cBFSyUtuELH53m38n1QarfU7u5-qV3VLPGY" ^
-H "Content-Type: application/json" ^
-d "{\"valor\": 85.00}"

echo.
echo Atualizacao parcial concluida!
pause 