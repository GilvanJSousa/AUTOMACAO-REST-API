@echo off
setlocal

mvn allure:report
mvn allure:serve
 
endlocal
exit /b 