# FootyStck API (Spring Boot)

Backend de FootyStck en **Java Spring Boot**, siguiendo la arquitectura por capas de
DWES: **Controller → Service → Repository + Model + DTO**.

## 🧱 Tecnologías
- Spring Boot 3.2.5 (Java 21)
- Spring Web (REST) · Spring Data JPA · Validation
- **H2** (base de datos en memoria, como en clase)
- Lombok · BCrypt (contraseñas) · JWT (hecho con el JDK)
- Swagger/OpenAPI (springdoc)

## 📂 Estructura (paquete `com.footystck`)
```
config/      WebConfig (CORS + interceptor), BeansConfig (BCrypt)
model/       Entidades JPA: Usuario, Liga, Equipo, Camiseta, Pedido, PedidoItem
dto/         DTOs de entrada/salida + DTOMapper
repository/  Interfaces JpaRepository
service/     Logica de negocio (Auth, Camiseta, Liga, Equipo, Pedido, Sync)
controller/  Endpoints REST (/api/...)
security/    JwtUtil + SeguridadInterceptor + UsuarioAutenticado
exception/   ApiException + GlobalExceptionHandler
```

## ▶️ Cómo ejecutarlo (en casa, con internet)

### Opción A — IntelliJ (recomendada, como en clase)
1. **File → Open** y selecciona la carpeta `backend`.
2. IntelliJ detecta el `pom.xml` e **importa Maven** (descarga las librerías).
3. Abre `FootystckApplication.java` y pulsa el **▶ verde** (Run).
4. Arranca en **http://localhost:8080**.

### Opción B — Terminal (Maven Wrapper)
```bash
cd backend
mvnw.cmd spring-boot:run
```

> La **primera vez** Maven descarga las librerías de internet (por eso necesitas
> una red sin bloqueos, como la de casa). Después ya funciona siempre.

## 🔌 Conectar con el frontend Angular
En `frontend/src/app/config.ts` ya está puesta la dirección del backend (puerto **8080**):
```ts
export const API_URL = 'http://localhost:8080/api';
```

## 👤 Usuarios de prueba (vienen en data.sql)
| Rol     | Email                   | Contraseña |
|---------|-------------------------|------------|
| Admin   | admin@footystck.com     | admin123   |
| Cliente | cliente@footystck.com   | cliente123 |

## 🔐 Seguridad por roles (lo que pediste)
- El **catálogo** (GET) y el **login/registro** son públicos.
- **Crear/editar/borrar** camisetas y equipos, **sincronizar** y **ver todos los
  pedidos** → solo **admin** (lo controla `SeguridadInterceptor` comprobando el rol del JWT).
- Hacer pedidos y ver "mis pedidos" → cualquier usuario logueado.

## 🛠️ Herramientas útiles
- Consola H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:footystck`, user `sa`, sin contraseña)
- Swagger UI: http://localhost:8080/swagger-ui.html

## ⚽ API-Football
Pon tu API key en `src/main/resources/application.properties` (`apifootball.key=...`)
y, como admin, haz `POST /api/sync/ligas` para importar ligas y equipos.
