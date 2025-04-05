package com.digiwardrobe.exceptions;

import java.util.UUID;

public class ClothingItemNotFoundException extends RuntimeException {

    public ClothingItemNotFoundException(final UUID itemId) {
        super("Clothing item with ID " + itemId + " not found.");
    }
}