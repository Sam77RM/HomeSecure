package com.utp.homesecure.user.application.assemblers;

import com.utp.homesecure.user.application.dtos.UserDto;
import com.utp.homesecure.user.domain.aggregates.User;

public final class UserDtoAssembler {
    private UserDtoAssembler() {}
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getAccountId().userAccountId(),
                user.getFirstName(), user.getLastName(), user.getEmail().value(),
                user.getRole(), user.getStatus(), user.getCreatedAt(),
                user.getAuthorizedCameraIds());
    }
}
