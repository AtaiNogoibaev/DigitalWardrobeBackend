package com.digiwardrobe.exceptions;

import java.util.UUID;

public class ClothingItemWrongUserException extends RuntimeException {
    public ClothingItemWrongUserException(final UUID userId, final UUID itemId) {
        super("Clothing item with ID " + itemId + " does not belong to user with ID " + userId + ".");
    }
}
