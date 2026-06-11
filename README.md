# FootyStck

Tienda web de venta y personalización de camisetas de fútbol. Proyecto final del
Ciclo Formativo de Grado Superior de **Desarrollo de Aplicaciones Web (DAW)**, de
**Enrique Gutiérrez Ramírez**.

## Tecnologías

| Capa              | Tecnología                                                              |
|-------------------|-------------------------------------------------------------------------|
| **Frontend**      | Angular 21 (standalone), HTML5 y CSS propio                             |
| **Backend**       | Java 21 + Spring Boot 3.2.5 (API REST por capas)                        |
| **Base de datos** | MySQL 8                                                                  |
| **API externa**   | [API-Football](https://www.api-football.com) (ligas, equipos y escudos) |

## Estructura

```
Proyecto FootyStck/
├── frontend/   Aplicación Angular
├── backend/    API REST con Spring Boot (Java)
└── memoria/    Memoria del proyecto en PDF
```

El backend sigue una arquitectura por capas: `controller` → `service` → `repository` → `model` / `dto` (con `DTOMapper`).

## Cómo ejecutarlo

### 1) Base de datos (MySQL)
- Tener **MySQL Server 8** en marcha.
- La aplicación **crea sola** la base de datos `footystck` en el primer arranque.
- Usuario y contraseña se configuran en `backend/src/main/resources/application.properties` (por defecto `root` / `1234`).

### 2) Backend (Java) — arranca primero
- **Con IntelliJ:** abre la carpeta `backend`, deja que importe Maven y ejecuta `FootystckApplication`.
- **Con terminal:**
  ```bash
  cd backend
  mvnw.cmd spring-boot:run
  ```
- Queda en **http://localhost:8080**. Al arrancar carga datos de ejemplo desde `data.sql`.

### 3) Frontend (Angular)
```bash
cd frontend
npm install
npm start
```
- Abre **http://localhost:4200**.

## Usuarios de prueba
| Rol     | Email                   | Contraseña  |
|---------|-------------------------|-------------|
| Admin   | admin@footystck.com     | admin123    |
| Cliente | cliente@footystck.com   | cliente123  |

## Funcionalidades
- **Catálogo** de camisetas ordenado por popularidad, con buscador y filtro por liga y equipo.
- **Personalización en vivo**: talla (S–4XL), nombre y dorsal (+2 €) que se ven sobre un dibujo de la camiseta que copia el color del equipo.
- **Carrito** y **proceso de compra en dos pantallas**: datos de envío + desglose (base sin IVA, IVA 21 %, descuento y total). Envío gratis.
- **Códigos promocionales** de descuento (`FOOTY5/10/15/20`).
- **Minijuego de penaltis** tras la compra: por cada 20 € un lanzamiento; si marcas ganas un cupón (`GOL5/10/15`).
- Área de cliente: **registro, login y "Mis pedidos"**.
- **Panel de administración** (CRUD) de camisetas, ligas, equipos y pedidos.
- **Modo claro / oscuro**.
- Importación de ligas y equipos desde **API-Football**.

## Memoria
La memoria completa del proyecto está en [`memoria/Memoria FootyStck.pdf`](memoria/).
