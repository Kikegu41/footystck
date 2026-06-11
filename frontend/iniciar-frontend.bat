@echo off
REM ==== Arranca el frontend Angular en http://localhost:4200 ====
cd /d "%~dp0"
echo.
echo  Iniciando FRONTEND en http://localhost:4200 ...
echo  (espera a ver "Compiled successfully")
echo.
call npm start
echo.
echo  El frontend se ha detenido. Pulsa una tecla para cerrar.
pause >nul
