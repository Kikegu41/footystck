# FootyStck API (Spring Boot)

Backend de FootyStck en Java con Spring Boot, siguiendo la arquitectura por capas de
DWES: Controller -> Service -> Repository + Model + DTO.

## Tecnologías
- Spring Boot 3.2.5 (Java 21)
- Spring Web (REST), Spring Data JPA, Bean Validation
- MySQL 8
- Lombok

## Estructura (paquete `com.footystck`)
```
config/      WebConfig (CORS)
model/       Entidades JPA: Usuario, Liga, Equipo, Camiseta, Pedido, PedidoItem
dto/         DTOs de entrada/salida + DTOMapper
repository/  Interfaces JpaRepository
service/     Lógica de negocio (Auth, Camiseta, Liga, Equipo, Pedido, Sync)
controller/  Endpoints REST (/api/...)
exception/   GlobalExceptionHandler
```

## Cómo ejecutarlo

### Base de datos
- Tener MySQL Server 8 en marcha.
- La aplicación crea sola la base de datos `footystck` en el primer arranque.
- Usuario y contraseña en `src/main/resources/application.properties` (por defecto `root` / `1234`).

### Opción A — IntelliJ (recomendada)
1. File -> Open y selecciona la carpeta `backend`.
2. IntelliJ detecta el `pom.xml` e importa Maven (descarga las librerías).
3. Abre `FootystckApplication.java` y pulsa Run.
4. Arranca en http://localhost:8080 y carga los datos de ejemplo de `data.sql`.

### Opción B — Terminal (Maven Wrapper)
```bash
cd backend
mvnw.cmd spring-boot:run
```

## Conectar con el frontend Angular
En `frontend/src/app/config.ts` está la dirección del backend (puerto 8080):
```ts
export const API_URL = 'http://localhost:8080/api';
```

## Usuarios de prueba (vienen en data.sql)
| Rol     | Email                   | Contraseña |
|---------|-------------------------|------------|
| Admin   | admin@footystck.com     | admin123   |
| Cliente | cliente@footystck.com   | cliente123 |

El login es básico (sin token): el servicio compara el email y la contraseña y devuelve
el usuario con su rol. El acceso al panel de administración se controla en el frontend
según ese rol.

## API-Football
La clave va en `src/main/resources/application.properties` (`apifootball.key=...`).
Como admin, `POST /api/sync/ligas` importa ligas y equipos. La aplicación también
funciona sin conexión a la API, con los datos de ejemplo de `data.sql`.
