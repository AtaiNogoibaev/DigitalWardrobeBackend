package com.digiwardrobe.exceptions;

import java.util.UUID;

public class EitherClothingOrAccessoryException extends RuntimeException {
    public EitherClothingOrAccessoryException(final UUID outfitId) {
        super(String.format("An outfit %s must have either a clothing item or an accessory", outfitId));
    }
}
