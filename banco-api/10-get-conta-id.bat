@echo off
echo Obtendo detalhes da conta bancaria...
echo.

curl -X GET http://localhost:3000/contas/6866b72dbb33cc30f0413b6d ^
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY4NjZiNzJkYmIzM2NjMzBmMDQxM2I3NCIsInVzZXJuYW1lIjoiYWRtaW4iLCJpYXQiOjE3NTE1NjUyMTEsImV4cCI6MTc1MTU2ODgxMX0.jit2bJu6cBFSyUtuELH53m38n1QarfU7u5-qV3VLPGY"

echo.
echo Consulta concluida!
pause 