package com.utp.homesecure.user.interfaces.rest.documentation;

import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.interfaces.rest.resources.CreateUserRequest;
import com.utp.homesecure.user.interfaces.rest.resources.SetUserActiveRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;

/** Contrato OpenAPI separado del controlador para documentar el agregado User. */
@Tag(name = "Users", description = "Operaciones de gestión de usuarios de HomeSecure")
public interface UserApi {
    @Operation(summary = "Registrar usuario", description = "Crea una cuenta User y genera su accountId UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "El correo ya existe")
    })
    ResponseEntity<UserDto> create(CreateUserRequest request);

    @Operation(summary = "Listar usuarios", description = "Devuelve los usuarios registrados sin exponer la contraseña.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios")
    List<UserDto> list();

    @Operation(summary = "Buscar usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    UserDto get(@Parameter(description = "ID interno del usuario", required = true) Long id);

    @Operation(summary = "Activar o desactivar usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    UserDto setActive(Long id, SetUserActiveRequest request);
}
