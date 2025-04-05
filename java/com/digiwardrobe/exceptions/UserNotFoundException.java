package com.digiwardrobe.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final UUID userId) {
        super("User with ID " + userId + " not found.");
    }

}
