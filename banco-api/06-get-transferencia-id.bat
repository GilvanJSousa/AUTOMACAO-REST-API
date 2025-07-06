@echo off
echo Obtendo transferencia especifica...
echo.

curl -X GET http://localhost:3000/transferencias/6866beb554f7e3896d3a9f39 -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY4NjZiNzJkYmIzM2NjMzBmMDQxM2I3NCIsInVzZXJuYW1lIjoiYWRtaW4iLCJpYXQiOjE3NTE1NjM5MzYsImV4cCI6MTc1MTU2NzUzNn0.Y-3KQJxMiPTxkJYllvs7y1-puFThUr82PA9Br2etnew"

echo.
echo Teste concluido!
pause 