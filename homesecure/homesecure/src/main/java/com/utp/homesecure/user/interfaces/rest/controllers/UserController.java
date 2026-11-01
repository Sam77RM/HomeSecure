package com.utp.homesecure.user.interfaces.rest.controllers;

import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.application.handlers.commands.UserCommandHandler;
import com.utp.homesecure.user.application.handlers.queries.GetUserQueryHandler;
import com.utp.homesecure.user.application.handlers.queries.ListUsersQueryHandler;
import com.utp.homesecure.user.application.queries.GetUserQuery;
import com.utp.homesecure.user.application.queries.ListUsersQuery;
import com.utp.homesecure.user.interfaces.rest.documentation.UserApi;
import com.utp.homesecure.user.interfaces.rest.mappers.UserRestMapper;
import com.utp.homesecure.user.interfaces.rest.resources.CreateUserRequest;
import com.utp.homesecure.user.interfaces.rest.resources.SetUserActiveRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserCommandHandler commands;
    private final GetUserQueryHandler getUser;
    private final ListUsersQueryHandler listUsers;

    public UserController(UserCommandHandler commands, GetUserQueryHandler getUser,
                          ListUsersQueryHandler listUsers) {
        this.commands = commands;
        this.getUser = getUser;
        this.listUsers = listUsers;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commands.handle(UserRestMapper.toCommand(request)));
    }

    @Override
    @GetMapping
    public List<UserDto> list() {
        return listUsers.handle(new ListUsersQuery());
    }

    @Override
    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return getUser.handle(new GetUserQuery(id));
    }

    @Override
    @PatchMapping("/{id}/active")
    public UserDto setActive(@PathVariable Long id, @RequestBody SetUserActiveRequest request) {
        return commands.handle(UserRestMapper.toCommand(id, request));
    }
}
