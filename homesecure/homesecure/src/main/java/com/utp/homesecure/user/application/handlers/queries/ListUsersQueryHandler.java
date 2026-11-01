package com.utp.homesecure.user.application.handlers.queries;

import com.utp.homesecure.user.application.assemblers.UserDtoAssembler;
import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.application.queries.ListUsersQuery;
import com.utp.homesecure.user.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListUsersQueryHandler {
    private final UserRepository users;
    public ListUsersQueryHandler(UserRepository users) { this.users = users; }
    public List<UserDto> handle(ListUsersQuery query) {
        return users.findAll().stream().map(UserDtoAssembler::toDto).toList();
    }
}
