package com.digiwardrobe.exceptions;

import java.util.UUID;

public class NullClothingAndAccessoryException extends RuntimeException {

    public NullClothingAndAccessoryException(final UUID outfitId) {
        super(String.format("An outfit item %s must have at least one item", outfitId));
    }
}
