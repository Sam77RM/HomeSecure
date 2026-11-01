package com.utp.homesecure.user.application.handlers.queries;

import com.utp.homesecure.user.application.assemblers.UserDtoAssembler;
import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.application.queries.GetUserQuery;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetUserQueryHandler {
    private final UserRepository users;
    public GetUserQueryHandler(UserRepository users) { this.users = users; }
    public UserDto handle(GetUserQuery query) {
        return UserDtoAssembler.toDto(users.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado")));
    }
}
