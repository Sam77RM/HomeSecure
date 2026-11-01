package com.utp.homesecure.user.interfaces.rest.mappers;
import com.utp.homesecure.user.application.commands.CreateUserCommand;
import com.utp.homesecure.user.application.commands.SetUserActiveCommand;
import com.utp.homesecure.user.interfaces.rest.resources.CreateUserRequest;
import com.utp.homesecure.user.interfaces.rest.resources.SetUserActiveRequest;

public final class UserRestMapper {
    private UserRestMapper() {}
    public static CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(request.firstName(), request.lastName(), request.email(),
                request.passwordHash(), request.role());
    }
    public static SetUserActiveCommand toCommand(Long id, SetUserActiveRequest request) {
        return new SetUserActiveCommand(id, request.active());
    }
}
