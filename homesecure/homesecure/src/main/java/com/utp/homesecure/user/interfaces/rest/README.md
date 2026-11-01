# REST API - User

Base URL: `/api/v1/users`

## Operaciones

| Método | Ruta | Descripción | Respuesta habitual |
|---|---|---|---|
| POST | `/api/v1/users` | Crear usuario | `201 Created` y `UserDto` |
| GET | `/api/v1/users` | Listar usuarios | `200 OK` y lista de `UserDto` |
| GET | `/api/v1/users/{id}` | Consultar por ID | `200 OK`; error si no existe |
| PATCH | `/api/v1/users/{id}/active` | Cambiar estado activo | `200 OK` y `UserDto` |

## Ejemplo de creación

```json
{
  "firstName": "Ana",
  "lastName": "Perez",
  "email": "ana@example.com",
  "passwordHash": "<hash-generado-en-servidor>",
  "role": "OWNER"
}
```

`passwordHash` debe ser un hash seguro, nunca una contraseña en texto plano. Ajusta los nombres de campos al record/clase `CreateUserRequest` del proyecto.

## Activar/desactivar

`PATCH /api/v1/users/1/active`
```json
{ "active": false }
```

## Clases
- `UserController`: mapea rutas y códigos HTTP.
- `UserApi`: contrato documentado separado del controlador, análogo a `StudentApi` de Learning Platform.
- `UserRestMapper`: transforma request a command.
- `CreateUserRequest` y `SetUserActiveRequest`: modelos de entrada.
