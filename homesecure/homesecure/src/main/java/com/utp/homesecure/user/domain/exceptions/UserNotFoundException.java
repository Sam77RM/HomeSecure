package com.utp.homesecure.user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) { super(message); }
}
