Write-Host "========================================" -ForegroundColor Green
Write-Host "    Executando Testes com Allure" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

Write-Host "1. Limpando projeto..." -ForegroundColor Yellow
mvn clean

Write-Host ""
Write-Host "2. Executando testes..." -ForegroundColor Yellow
mvn test

Write-Host ""
Write-Host "3. Gerando relatorio Allure..." -ForegroundColor Yellow
mvn allure:report

Write-Host ""
Write-Host "4. Abrindo relatorio no navegador..." -ForegroundColor Yellow
mvn allure:serve

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "    Processo concluido!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Read-Host "Pressione Enter para sair" 