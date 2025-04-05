package com.digiwardrobe.exceptions;

public class ClothingFitNotFoundException extends RuntimeException {

    public ClothingFitNotFoundException(final int fitId) {
        super("Clothing fit with id " + fitId + " not found");
    }
}
