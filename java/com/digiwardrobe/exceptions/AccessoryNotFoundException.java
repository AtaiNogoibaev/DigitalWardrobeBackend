package com.digiwardrobe.exceptions;

import java.util.UUID;

public class AccessoryNotFoundException extends RuntimeException {
    public AccessoryNotFoundException(final UUID accessoryId) {
        super("Accessory with id " + accessoryId + " not found");
    }
}
