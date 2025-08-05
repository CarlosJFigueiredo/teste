@echo off
echo ====================================
echo    Drone Delivery System
echo ====================================
echo.

echo [1/3] Iniciando backend (Spring Boot)...
start cmd /k "cd /d %~dp0 && mvn spring-boot:run"

echo [2/3] Aguardando 10 segundos para o backend inicializar...
timeout /t 10 /nobreak > nul

echo [3/3] Iniciando frontend (React)...
start cmd /k "cd /d %~dp0drone-delivery-frontend && npm start"

echo.
echo ====================================
echo Sistema iniciado com sucesso!
echo.
echo Backend: http://localhost:8080
echo Frontend: http://localhost:3000
echo ====================================
echo.
echo Pressione qualquer tecla para sair...
pause > nul
