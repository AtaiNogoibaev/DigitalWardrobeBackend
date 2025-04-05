package com.digiwardrobe.exceptions;

import java.util.UUID;

public class AccessoryWrongUserException extends RuntimeException {
    public AccessoryWrongUserException(final UUID userId, final UUID accessoryId) {
        super(String.format("User %s does not own Accessory id %s", userId, accessoryId));
    }
}
