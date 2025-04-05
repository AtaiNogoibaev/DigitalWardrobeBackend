package com.digiwardrobe.exceptions;

public class ClothingTypeNotFoundException extends RuntimeException {

    public ClothingTypeNotFoundException(final int typeId) {
        super("Clothing type with id " + typeId + " not found");
    }
}
