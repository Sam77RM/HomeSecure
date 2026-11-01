# Arquitectura del módulo User (HomeSecure)

Este proyecto mantiene el dominio de HomeSecure y añade la organización de Application e Interfaces tomando Learning Platform como referencia. El alcance funcional de este paquete es exclusivamente `user`; Monitoring se conserva como módulo independiente.

## Capas del módulo

- `user/domain`: Aggregate Root `User`, value objects, eventos, enums, excepciones y puerto `UserRepository`.
- `user/application`: commands, queries, handlers, DTO y assembler. Coordina casos de uso y no recibe HTTP directamente.
- `user/interfaces/rest`: controller, requests, mapper y `documentation/UserApi`.
- `user/infrastructure/persistence/jpa`: entidad JPA, mapper, repositorio Spring Data y adaptador del puerto de dominio.

## Recorrido de creación

1. Cliente envía `POST /api/v1/users`.
2. `UserController` recibe `CreateUserRequest`.
3. `UserRestMapper` convierte el request en `CreateUserCommand`.
4. `UserCommandHandler` coordina el caso de uso.
5. `User` aplica las reglas de dominio.
6. `UserRepository` se implementa en infraestructura mediante `UserRepositoryAdapter`.
7. `UserDtoAssembler` construye `UserDto` para la respuesta.

## Importante

La documentación `UserApi` es un contrato Java con JavaDoc, no una interfaz de Swagger/OpenAPI. Para Swagger UI se debe añadir una dependencia compatible de springdoc y anotar las operaciones. La compilación debe probarse en el entorno local con Java 21 y Maven Wrapper.
