@echo off
REM ==== Arranca el backend Spring Boot en http://localhost:8080 ====
cd /d "%~dp0"
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.7.6-hotspot"
set "JAVA_TOOL_OPTIONS=-Djavax.net.ssl.trustStore=C:\Users\PC\.m2\cacerts -Djavax.net.ssl.trustStorePassword=changeit"
echo.
echo  Iniciando BACKEND en http://localhost:8080 ...
echo  (espera a ver "Started ... in X seconds")
echo.
call mvnw.cmd -o spring-boot:run
echo.
echo  El backend se ha detenido. Pulsa una tecla para cerrar.
pause >nul
