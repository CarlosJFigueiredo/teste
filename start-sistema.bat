@echo off
echo ================================================================
echo Sistema de Entrega com Drones - DTI Digital
echo ================================================================
echo.

echo Compilando o projeto...
cd /d "c:\Users\KABUM\Desktop\teste\drone-delivery"
call mvn clean compile -q

if %ERRORLEVEL% neq 0 (
    echo ERRO: Falha na compilacao!
    pause
    exit /b 1
)

echo ✅ Compilacao concluida com sucesso!
echo.

echo Instalando dependencias do frontend...
cd /d "c:\Users\KABUM\Desktop\teste\drone-delivery-frontend"
call npm install --silent

if %ERRORLEVEL% neq 0 (
    echo ERRO: Falha na instalacao das dependencias!
    pause
    exit /b 1
)

echo ✅ Dependencias instaladas com sucesso!
echo.

echo ================================================================
echo Iniciando o sistema...
echo ================================================================
echo.
echo 🚀 Backend: http://localhost:8080
echo 🌐 Frontend: http://localhost:3000
echo.
echo Pressione Ctrl+C para parar os servicos
echo ================================================================

rem Iniciar backend em background
start "Backend - Spring Boot" cmd /c "cd /d c:\Users\KABUM\Desktop\teste\drone-delivery && mvn spring-boot:run"

rem Aguardar 10 segundos para o backend inicializar
timeout /t 10 /nobreak >nul

rem Iniciar frontend
start "Frontend - React" cmd /c "cd /d c:\Users\KABUM\Desktop\teste\drone-delivery-frontend && npm start"

echo.
echo ✅ Sistema iniciado com sucesso!
echo.
echo 📊 Acesse o Dashboard em: http://localhost:3000
echo 🔧 API Backend em: http://localhost:8080/api
echo.
echo Pressione qualquer tecla para sair...
pause >nul
