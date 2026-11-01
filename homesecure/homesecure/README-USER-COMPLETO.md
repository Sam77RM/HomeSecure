# HomeSecure — módulo User completo

Este proyecto contiene únicamente el contexto User; no incluye código de Monitoring. Está organizado siguiendo el estilo del proyecto HomeSecure existente y conserva `com.utp.homesecure` como paquete base.

## Requisitos
- JDK 21
- IntelliJ IDEA o VS Code
- MySQL

## Configuración
1. Abre esta carpeta (`homesecure`) como proyecto Maven.
2. Revisa `src/main/resources/application.properties` y configura el usuario/contraseña de MySQL de tu equipo.
3. La URL configurada crea la base de datos `homesecure` si no existe.
4. Ejecuta `HomeSecureApplication`.
5. Swagger UI: `http://localhost:8080/swagger-ui.html`.

## Endpoints
- `POST /api/v1/users` — registrar usuario.
- `GET /api/v1/users` — listar usuarios.
- `GET /api/v1/users/{id}` — buscar usuario por ID.
- `PATCH /api/v1/users/{id}/active` — activar o desactivar usuario con `{ "active": true }` o `{ "active": false }`.

## Organización
- `domain/model/aggregates/User`: Aggregate Root, reglas e invariantes.
- `domain/model/valueobjects`: `Email`, `PasswordHash`, `UserAccountId`.
- `domain/repositories/UserRepository`: puerto del dominio.
- `application`: comandos, consultas, handlers y DTO.
- `infrastructure/persistence/jpa`: entidad, Spring Data JPA, mapper y adaptador.
- `interfaces/rest`: controlador, recursos, mapper REST y contrato OpenAPI en `documentation/UserApi`.

## Importante sobre contraseñas
El request existente usa el campo `passwordHash`, por lo que debe recibir un hash generado por la capa de seguridad y nunca una contraseña en texto plano. Este módulo no incluye login, JWT ni autorización de rutas.

## Integración futura
`UserAccountId` es un UUID estable que puede utilizarse como identificador de cuenta al integrar el contexto Monitoring. Este ZIP deliberadamente no contiene el código de Monitoring.
