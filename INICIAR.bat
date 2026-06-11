@echo off
REM ==== Abre los dos servidores de FootyStck en ventanas separadas ====
echo Abriendo backend y frontend en dos ventanas...
start "FootyStck - BACKEND (8080)" cmd /k "%~dp0backend\iniciar-backend.bat"
start "FootyStck - FRONTEND (4200)" cmd /k "%~dp0frontend\iniciar-frontend.bat"
echo.
echo  Espera alrededor de 1 minuto a que las dos ventanas terminen de cargar
echo  y luego abre en el navegador:  http://localhost:4200
echo.
pause >nul
