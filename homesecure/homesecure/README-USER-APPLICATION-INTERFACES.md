# HomeSecure - módulo User con Application e Interfaces

Este proyecto conserva la lógica de dominio `user` de HomeSecure y añade las capas `application` e `interfaces/rest`, siguiendo la organización que utiliza `learning-platform` para Student. No se implementan endpoints para Monitoring ni se modifica su lógica.

## Capas y recorrido

`HTTP request → UserController → UserRestMapper → Command/Query → Handler → User (Aggregate Root) → UserRepository (port) → UserRepositoryAdapter → UserJpaRepository → MySQL`

La respuesta recorre el camino inverso y se convierte a `UserDto`.

- `domain`: reglas del usuario, Aggregate Root `User`, objetos de valor, eventos y puerto `UserRepository`.
- `application/commands`: intención de cambiar datos (crear usuario y activar/desactivar).
- `application/queries`: intención de consultar datos (buscar uno o listar).
- `application/handlers`: ejecutan los casos de uso y coordinan el dominio con el repositorio.
- `application/dtos` y `assemblers`: datos de salida para la API; no se devuelve directamente la entidad JPA.
- `interfaces/rest/resources`: estructura de los JSON que recibe la API.
- `interfaces/rest/mappers`: traduce los recursos HTTP a comandos.
- `interfaces/rest/controllers`: expone los endpoints REST.
- `infrastructure/persistence`: adapter, mapper JPA, entidad de persistencia y repositorio Spring Data.

## Endpoints

Base URL: `/api/v1/users`

- `POST /api/v1/users` crea un usuario.
- `GET /api/v1/users` lista usuarios.
- `GET /api/v1/users/{id}` consulta un usuario.
- `PATCH /api/v1/users/{id}/active` activa o desactiva un usuario.

### Ejemplo POST

```json
{
  "firstName": "Ana",
  "lastName": "Perez",
  "email": "ana@example.com",
  "passwordHash": "$2a$10$REEMPLAZAR_POR_UN_HASH_BCRYPT_REAL",
  "role": "OWNER"
}
```

**Importante sobre contraseñas:** `passwordHash` debe ser un hash real creado por una capa de seguridad (por ejemplo, BCrypt). No envíes una contraseña en texto plano en ese campo. Este módulo no añade todavía autenticación ni generación de hash; eso debe añadirse antes de usarlo en producción. Si se integra un `PasswordEncoder`, el request puede recibir `password` y el handler debe generar el hash antes de crear `PasswordHash`.

Valores de `role`: `ADMINISTRATOR`, `OWNER`, `GUEST`. Si se omite, el handler usa `OWNER`.

### Ejemplo PATCH

`PATCH /api/v1/users/1/active`

```json
{
  "active": false
}
```

## Abrir y ejecutar

1. Descomprime el ZIP.
2. Abre la carpeta que contiene `pom.xml` en IntelliJ IDEA o VS Code.
3. Configura Java 21 y una instancia MySQL.
4. Revisa `src/main/resources/application.properties` y confirma que URL, usuario y contraseña de MySQL correspondan a tu entorno.
5. Ejecuta `HomesecureApplication`.

La compilación/ejecución depende de que MySQL y las propiedades locales estén correctamente configurados.

## Qué falta para producción

- Autenticación y autorización (Spring Security).
- Hash de contraseñas dentro del flujo de registro, nunca aceptar un hash arbitrario del cliente.
- Validación HTTP con Bean Validation y manejo de errores consistente (`@ControllerAdvice`).
- Pruebas unitarias e integración.
- Revisar la relación de cámaras autorizadas cuando se conecte el módulo Monitoring.
